package net.homecredit.koyal.salesfeed

import net.homecredit.koyal.salesfeed.SalesFeedConstants._
import org.slf4j.LoggerFactory

case class Salesroom(var code: String = EmptyString,
                     var name: String = EmptyString,
                     var partnerCode: String = EmptyString,
                     var partnerName: String = EmptyString,
                     var gpsLatitude: String = EmptyString,
                     var gpsLongitude: String = EmptyString,
                     var countryCode: String = EmptyString,
                     var regionCode: String = EmptyString,
                     var districtCode: String = EmptyString,
                     var houseNumber: String = EmptyString,
                     var addressType: String = EmptyString,
                     var street: String = EmptyString,
                     var town: String = EmptyString) {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def buildFrom(sourceMap: Map[String, String]): Salesroom = {
    val salesroom = Salesroom()
    salesroom.code = sourceMap.getOrElse(CsvColumnNameSalesroomCode, EmptyString)
    salesroom.name = sourceMap.getOrElse(CsvColumnNameSalesroomName, EmptyString)
    salesroom.partnerCode = sourceMap.getOrElse(CsvColumnNameSalesroomPartnerCode, EmptyString)
    salesroom.partnerName = sourceMap.getOrElse(CsvColumnNameSalesroomPartnerName, EmptyString)

    //TODO rewrite the 2 ifs below to functional manner
    salesroom.gpsLatitude = sourceMap.getOrElse(CsvColumnNameSalesroomGpsLatitude, NullString)
    if (salesroom.gpsLatitude.isBlank) salesroom.gpsLatitude = NullString
    salesroom.gpsLongitude = sourceMap.getOrElse(CsvColumnNameSalesroomGpsLongitude, NullString)
    if (salesroom.gpsLongitude.isBlank) salesroom.gpsLongitude = NullString

    salesroom.countryCode = sourceMap.getOrElse(CsvColumnNameSalesroomCountryCode, EmptyString)
    salesroom.regionCode = sourceMap.getOrElse(CsvColumnNameSalesroomRegionCode, EmptyString)
    salesroom.districtCode = sourceMap.getOrElse(CsvColumnNameSalesroomDistrictCode, EmptyString)
    salesroom.houseNumber = sourceMap.getOrElse(CsvColumnNameSalesroomHouseNumber, EmptyString)
    salesroom.addressType = sourceMap.getOrElse(CsvColumnNameSalesroomAddressType, EmptyString)
    salesroom.street = sourceMap.getOrElse(CsvColumnNameSalesroomStreet, EmptyString)
    salesroom.town = sourceMap.getOrElse(CsvColumnNameSalesroomTown, EmptyString)

    logger.debug("salesroom: {}", salesroom)
    salesroom
  }

}
