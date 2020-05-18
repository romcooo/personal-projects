package org.romcooo.kickstart2020c2

import org.romcooo.kickstart2020c.isDescendingTo1
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import kotlin.streams.toList

fun main() {
    val requirement = parseInput()
//    println(requirement)
    for (i in 0 until requirement.size) {
        val tc = requirement.testCases[i]
        var tcN = 0
        for ()
        println(distChars(tc.rows[0]))
        for ()
        println("Case #${i+1}: $tcN")
    }
}

data class Requirement(var size: Int = 0, var testCases: MutableList<TestCase> = mutableListOf())

data class TestCase(val rowN: Int, val colN: Int, var rows: MutableList<String> = mutableListOf())

fun countUniqueChars(s: String): Int {
    return s.chars().distinct().count().toInt()
}

fun distChars(s: String): List<Char> {
    return s.toCharArray().distinct()
}

fun parseInput(): Requirement {
    val requirement = Requirement()
    val scanner = Scanner(BufferedReader(InputStreamReader(System.`in`)))
    requirement.size = scanner.nextInt()
    scanner.nextLine()
    for (i in 0 until requirement.size) {
        val firstLine = scanner.nextLine()
        if (firstLine.isBlank()) {
            break
        }
        val firstSplit = firstLine.split(" ")
        val nextCase = TestCase(rowN = firstSplit[0].toInt(), colN = firstSplit[1].toInt())
        for (row in 0 until nextCase.rowN) {
            val nextLine = scanner.nextLine()
            nextCase.rows.add(nextLine)
        }
        requirement.testCases.add(nextCase)
    }

    scanner.close()
    return requirement;
}