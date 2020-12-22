package net.homecredit.koyal.common

import org.slf4j.LoggerFactory

import scala.io.Source

object CsvParser extends Parser[Map[String, String]] {

  private val logger = LoggerFactory.getLogger(this.getClass)

  val CsvSeparator: String = ";"
  val CsvComment: String = "##"
  val WhitespaceTrimPattern: String = "\\s*" + CsvSeparator + "\\s*"

  private def parseLine(line: String, columnNames: List[String]): Map[String, String] = {
    logger.debug("Parsing line {}", line)
    line.split(CsvSeparator)
        .zipWithIndex
        .map { case (value, index) => (columnNames(index) -> value) }
        .toMap
  }

  private def getColumnNames(lines: List[String]): List[String] = {
    logger.debug("Extracting column names")
    lines.take(1)
        .map(line => line.split(CsvSeparator))
        .flatMap(columnNamesArray => columnNamesArray.toList)
  }

  private def getColumnValues(lines: List[String], columnNames: List[String]): List[Map[String, String]] = {
    logger.debug("Transforming values to list of maps")
    lines.drop(1)
        .map(line => parseLine(line, columnNames))
  }

  private def getFileLines(sourceFile: String): List[String] = {
    logger.debug("Parsing file {}", sourceFile)
    val source = Source.fromFile(sourceFile)
    try {
      source.getLines()
          .filter(line => !line.startsWith(CsvComment) && !line.isEmpty)
          .map(l => l.replaceAll(WhitespaceTrimPattern, CsvSeparator)) //trim whitespace around separator
          .toList
    } catch {
      case e: Throwable => throw new IllegalArgumentException("An exception was thrown while reading CSV file " + sourceFile, e)
    }
    finally {
      source.close()
    }
  }

  override def parse(sourceFile: String): List[Map[String, String]] = {
    val csvLines = getFileLines(sourceFile)
    getColumnValues(csvLines, getColumnNames(csvLines))
  }

}
