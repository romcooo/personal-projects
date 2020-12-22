package net.homecredit.koyal.common

import java.time.LocalDate
import java.util.Base64

import org.slf4j.LoggerFactory

import scala.util.Random

object Utils extends Constants {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def randomValueFromList[A](list: List[A]): A = {
    list(Rnd.nextInt(list.length))
  }

  def randomValueFromMapOfLists[A](key: String, mapOfStringLists: Map[String, List[A]]): A = {
    val list = mapOfStringLists(key)
    list(Rnd.nextInt(list.length))
  }

  def randomString(length: Int): String = {
    Rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def randomNumericString(length: Int): String = {
    Rnd.alphanumeric.filter(_.isDigit).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(DatePattern)
  }

  def mapFromCsvAsList(csvAsList: List[Map[String, String]], keyName: String, valueName: String): Map[String, Int] = {
    var distributionMap: Map[String, Int] = Map()
    for (csvRow <- csvAsList) {
      distributionMap += (csvRow(keyName) -> csvRow(valueName).toInt)
    }
    distributionMap
  }

  def foldMapValuesAscending[A](map: Map[A, Int]): Map[A, Int] = {
    map.scanLeft((map.keySet.head, 0)) {
      case ((_, acc), (x, y)) => (x, acc + y)
    }
  }

  def probableKeyFromDistributionMap[A](distributionMap: Map[A, Int]): A = {
    val sumOfDistributions = distributionMap.values.sum
    val p = Rnd.nextInt(sumOfDistributions)
    logger.debug("mapOfProbablities: {}, probabilityIndex: {}", distributionMap.toString(), p)

    // DEBUG:
    //    val summedMap = sumMapValues(mapOfProbablities)
    //    logger.debug("summedMap: {}", summedMap.toString())
    //    val selected = summedMap.find(m => m._2 > p).get._1
    //    logger.debug("Random probability: {}, value selected: {}", p, selected)
    //    selected

    foldMapValuesAscending(distributionMap).find(m => m._2 > p).get._1
  }

  def getBasicAuthHeader(username: String, password: String): String = {
    "Basic ".concat(
      Base64
          .getEncoder
          .encodeToString(username
                              .concat(":")
                              .concat(password)
                              .getBytes()))
  }

  def getValueByNameFromKeyValueCsv(csvAsList: List[Map[String, String]], keyName: String): String = {
    logger.debug(csvAsList.toString())
    csvAsList
        .find(map => map.getOrElse(CsvColumnNameKey, EmptyString) == keyName)
        .getOrElse(Map())
        .getOrElse(CsvColumnNameValue, EmptyString)
  }
}
