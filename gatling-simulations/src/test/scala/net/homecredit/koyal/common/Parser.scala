package net.homecredit.koyal.common

trait Parser[A] {
  def parse(sourceFile: String): List[A]
}
