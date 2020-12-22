package net.homecredit.koyal.salesfeed

import net.homecredit.koyal.salesfeed.SalesFeedConstants._
import org.slf4j.LoggerFactory

case class CommodityDetail(var category: String = EmptyString,
                           var commodityType: String = EmptyString,
                           var producerCode: String = EmptyString,
                           var serialNumber: String = EmptyString,
                           var modelNumber: String = EmptyString,
                           var engineNumber: String = EmptyString,
                           var licencePlateNumber: String = EmptyString,
                           var price: String = EmptyString) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def buildFrom(sourceMap: Map[String, String]): CommodityDetail = {
    val commodityDetail = CommodityDetail()
    commodityDetail.commodityType = sourceMap.getOrElse(CsvColumnNameType, EmptyString)
    commodityDetail.producerCode = sourceMap.getOrElse(CsvColumnNameProducer, EmptyString)
    commodityDetail.serialNumber = sourceMap.getOrElse(CsvColumnNameSerialNumber, EmptyString)
    commodityDetail.modelNumber = sourceMap.getOrElse(CsvColumnNameModel, EmptyString)
    commodityDetail.engineNumber = sourceMap.getOrElse(CsvColumnNameEngineNumber, EmptyString)
    commodityDetail.licencePlateNumber = sourceMap.getOrElse(CsvColumnNameLicencePlateNumber, EmptyString)
    commodityDetail.price = sourceMap.getOrElse(CsvColumnNamePrice, EmptyString)
    commodityDetail.category = sourceMap.getOrElse(CsvColumnNameCategory, EmptyString)

    logger.debug("commodityDetail: {}", commodityDetail)
    commodityDetail
  }

}
