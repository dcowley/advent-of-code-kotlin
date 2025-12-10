package dev.dc.aoc.y24

import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputText(name: String) = Path("2024/src/dev.dc.aoc.y24.main/resources/$name.txt").readText().trim()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = readInputText(name).lines()
