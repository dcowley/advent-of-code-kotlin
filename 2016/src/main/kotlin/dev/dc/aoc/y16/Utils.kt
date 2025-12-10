package dev.dc.aoc.y16

import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputText(name: String) = Path("2016/src/dev.dc.aoc.y16.main/resources/$name.txt").readText().trim()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = readInputText(name).lines()
