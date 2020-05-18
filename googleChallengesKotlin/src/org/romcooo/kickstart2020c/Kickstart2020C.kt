package org.romcooo.kickstart2020c

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

fun main() {
    val requirement = parseInput()
//    println("req: $requirement")
    for (i in 0 until requirement.size) {
        val tc = requirement.testCases[i]
        var tcN = 0
        var next = tc.countdownFrom
        for (n in 0 until tc.numbers.size) {
            if (tc.numbers[n] == tc.countdownFrom && (n + tc.countdownFrom) <= tc.numbers.size) {
                val sub = tc.numbers.subList(n, n+tc.countdownFrom)
//                println(sub)
                if (isDescendingTo1(sub)) {
                    tcN++
                }
            }
        }
        println("Case #${i+1}: $tcN")
    }
}

data class Requirement(var size: Int = 0, var testCases: MutableList<TestCase> = mutableListOf())

data class TestCase(val size: Int, val countdownFrom: Int, var numbers: MutableList<Int> = mutableListOf())

fun isDescendingTo1(list: List<Int>): Boolean {
    var value = list[0]
    for (e in list.subList(1, list.size)) {
        if (e != value - 1) {
            return false
        }
        value--
    }
    return true
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
        val nextCase = TestCase(firstSplit[0].toInt(), firstSplit[1].toInt())
        val secondLine = scanner.nextLine()
        val lines = secondLine.split(" ")
        for (s in lines) {
            nextCase.numbers.add(s.toInt())
        }
        requirement.testCases.add(nextCase)
    }

    scanner.close()
    return requirement;
}
