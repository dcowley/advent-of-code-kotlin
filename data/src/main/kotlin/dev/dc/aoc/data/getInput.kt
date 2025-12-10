package dev.dc.aoc.data

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import okhttp3.Headers
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

private val sessionCookie by lazy {
    Path(".secrets/aoc-session-cookie").readText()
}

private val client: HttpClient
    get() = HttpClient(OkHttp) {
        defaultRequest {
            cookie(
                name = "session",
                value = sessionCookie
            )
            header("User-Agent", "https://github.com/dcowley/advent-of-code-kotlin by dean.w.cowley@gmail.com")
        }
    }

suspend fun getInput(year: Int, day: Int): String {
    val file = "Day" + "$day".padStart(2, '0') + ".txt"
    val path = Path("$year/src/main/resources/$file")

    return when {
        path.exists() -> path.readText()

        else -> {
            client.use {
                val response = it.get("https://adventofcode.com/$year/day/$day/input")
                response
                    .bodyAsText()
                    .trim()
                    .also(path::writeText)
            }
        }
    }
}
