package net.homecredit.koyal.salesfeed

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import net.homecredit.koyal.common.Utils._
import net.homecredit.koyal.common.{CsvParser, Utils}
import net.homecredit.koyal.salesfeed.SalesFeedConstants._
import org.slf4j.LoggerFactory

import scala.compat.Platform.EOL
import scala.concurrent.duration.DurationInt

object SalesFeed {

  private val logger = LoggerFactory.getLogger(this.getClass)
  private val producersDistributionCsv: List[Map[String, String]] = CsvParser.parse(ProducerDistributionFile)
  private val producersDistributionMap: Map[String, Int] = mapFromCsvAsList(producersDistributionCsv, CsvColumnNameProducer, CsvColumnNameDistribution)
  private val modelDistributionCsv: List[Map[String, String]] = CsvParser.parse(ModelDistributionFile)
  private val modelAttributesCsv: List[Map[String, String]] = CsvParser.parse(ModelAttributesFile)
  private val salesroomsCsv: List[Map[String, String]] = CsvParser.parse(SalesroomsFile)
  private val financialParametersCsv: List[Map[String, String]] = CsvParser.parse(FinancialParametersFile)

  private def generateRequestMapFromCsv() = {
    // producer and model:
    val producer = probableKeyFromDistributionMap(producersDistributionMap)
    val modelOfProducerList = modelDistributionCsv.filter(_ (CsvColumnNameProducer) == producer)
    val modelDistributionMap = mapFromCsvAsList(modelOfProducerList,
                                                CsvColumnNameModel,
                                                CsvColumnNameDistribution)
    val model = probableKeyFromDistributionMap(modelDistributionMap)

    // model attributes:
    val modelAttributeMap = modelAttributesCsv.filter(row => row(CsvColumnNameProducer) == producer)
        .find(row => row(CsvColumnNameModel).toLowerCase() == model.toLowerCase())
        .getOrElse(modelAttributesCsv
                       .find(row => row(CsvColumnNameProducer) == producer)
                       .getOrElse(randomValueFromList(modelAttributesCsv)))
    logger.debug("modelAttributeMap = {} ", modelAttributeMap)

    val commodityDetail = CommodityDetail().buildFrom(modelAttributeMap)
    val commodityDetails: List[CommodityDetail] = List(commodityDetail)

    // salesroom:
    val salesroomAttributesMap = randomValueFromList(salesroomsCsv)
    logger.debug("salesroomAttributesMap = {}", salesroomAttributesMap)

    val salesroom = Salesroom().buildFrom(randomValueFromList(salesroomsCsv))

    // financial parameters:
    val price: BigDecimal = if (commodityDetail.price.nonEmpty)
      BigDecimal(commodityDetail.price).setScale(2) else 0
    val term = randomValueFromList(financialParametersCsv).getOrElse(CsvColumnNameFinParamTerm, Utils.EmptyString).toInt
    val downPaymentPercentage = BigDecimal(randomValueFromList(financialParametersCsv).getOrElse(CsvColumnNameFinParamDownPaymentPercentage, "0"))
    val downPaymentAmount: BigDecimal = price * downPaymentPercentage
    val loanAmount: BigDecimal = price - downPaymentAmount
    val monthlyInstallment: BigDecimal = loanAmount / term

    // rest of main body
    val cuid = randomNumericString(18)
    val contractCode = randomNumericString(10)

    // build request:
    Map(
      "commodityDetails" -> commodityDetails,
      "salesroom" -> salesroom,
      "monthlyInstallment" -> monthlyInstallment,
      "term" -> term,
      "loanAmount" -> loanAmount,
      "downPayment" -> downPaymentAmount,
      "cuid" -> cuid,
      "contractCode" -> contractCode
      )
  }

  private val request = exec(
    http("call POST /save API with randomized data feeder and body template")
        .post("")
        .body(PebbleFileBody("bodies/salesfeed/salesFeedRequestAvailableDataOnlyTemplate.json")
              ).asJson
        .check(status.is(200)))

  private val customFeeder = Iterator.continually(generateRequestMapFromCsv())

  private def createChainBuilder() = {
    if (logger.isDebugEnabled) {
      logger.debug(
        List(
          "Sizes:",
          "producersDistributionCsv = " + producersDistributionCsv.size,
          "producersDistributionMap = " + producersDistributionMap.size,
          "modelDistributionCsv = " + modelDistributionCsv.size,
          "modelAttributesCsv = " + modelAttributesCsv.size,
          "salesroomsCsv = " + salesroomsCsv.size,
          "financialParametersCsv = " + financialParametersCsv.size,
          "Contents:",
          "producersDistributionCsv = " + producersDistributionCsv,
          "producersDistributionMap = " + producersDistributionMap,
          "modelDistributionCsv = " + modelDistributionCsv,
          "modelAttributesCsv = " + modelAttributesCsv,
          "salesroomsCsv = " + salesroomsCsv,
          "financialParametersCsv = " + financialParametersCsv
          ).mkString(EOL)
        )
    }

    feed(customFeeder)
        .exec(request)
        .pause(1.second)
  }

  val scenarioBuilder: ScenarioBuilder = scenario("Fixed Duration Load Simulation")
      .forever() {
        exec(createChainBuilder())
      }

}
