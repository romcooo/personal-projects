package net.homecredit.koyal.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.typesafe.config.ConfigFactory

import scala.util.Random

class Constants {
  val EmptyString: String = ""
  val NullString: String = "null"
  val CsvColumnNameKey = "key"
  val CsvColumnNameValue = "value"

  val NumberOfUsers: Int = Integer.getInteger("numberOfUsers", 2)
  val Duration: Int = Integer.getInteger("durationInSeconds", 10)

//  val Url: String = System.getProperty("targetUrl", "https://hcd-hosel-importer-webapi.dev.koyal.cloud/sales-feed")
  val Url: String = System.getProperty("targetUrl", "http://localhost:9999/")
  val DataFolderName: String = System.getProperty("targetFolderName", "india")

  val Rnd = new Random()

  val Now = LocalDate.now()

  val DatePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val Configuration = ConfigFactory.load()

  // don't commit this and below!
  val SalesfeedAccessUser: String = System.getProperty("koyalUser", "sales-feed")
  val SalesfeedAccessPassword: String = System.getProperty("koyalPassword", "cksjd#!985")

}
