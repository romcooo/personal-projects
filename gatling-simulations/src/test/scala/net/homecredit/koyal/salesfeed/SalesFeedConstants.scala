package net.homecredit.koyal.salesfeed

import net.homecredit.koyal.common.Constants

object SalesFeedConstants extends Constants {

  val ProducerDistributionFile = s"src/test/resources/data/${DataFolderName}/producersDistribution.csv"
  val ModelDistributionFile = s"src/test/resources/data/${DataFolderName}/modelsDistribution.csv"
  val ModelAttributesFile = s"src/test/resources/data/${DataFolderName}/modelAttributes.csv"
  val SalesroomsFile = s"src/test/resources/data/${DataFolderName}/salesrooms.csv"
  val FinancialParametersFile = s"src/test/resources/data/${DataFolderName}/financialParameters.csv"
  val CountrySettingsFile = s"src/test/resources/data/${DataFolderName}/countrySettings.csv"

  val CsvColumnNameDistribution = "distribution"
  val CsvColumnNameModel = "modelNumber"
  val CsvColumnNameProducer = "producer"
  val CsvColumnNameSerialNumber = "serialNumber"
  val CsvColumnNameEngineNumber = "engineNumber"
  val CsvColumnNameLicencePlateNumber = "licencePlateNumber"
  val CsvColumnNamePrice = "price"
  val CsvColumnNameType = "typeCode"
  val CsvColumnNameCategory = "category"

  val CsvColumnNameSalesroomCode = "code"
  val CsvColumnNameSalesroomName = "name"
  val CsvColumnNameSalesroomPartnerCode = "partnerCode"
  val CsvColumnNameSalesroomPartnerName = "partnerName"
  val CsvColumnNameSalesroomGpsLatitude = "gpsLatitude"
  val CsvColumnNameSalesroomGpsLongitude = "gpsLongitude"
  val CsvColumnNameSalesroomCountryCode = "countryCode"
  val CsvColumnNameSalesroomRegionCode = "regionCode"
  val CsvColumnNameSalesroomDistrictCode = "districtCode"
  val CsvColumnNameSalesroomHouseNumber = "houseNumber"
  val CsvColumnNameSalesroomAddressType = "addressType"
  val CsvColumnNameSalesroomStreet = "street"
  val CsvColumnNameSalesroomTown = "town"

  val CsvColumnNameFinParamTerm = "term"
  val CsvColumnNameFinParamDownPaymentPercentage = "downPaymentPercentage"

  val CurrencyKey = "currency"

}
