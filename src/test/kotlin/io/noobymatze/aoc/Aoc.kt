package io.noobymatze.aoc

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.time.Year
import java.util.Properties
import kotlin.io.path.exists
import kotlin.io.path.inputStream

object Aoc {

    val intRegex = Regex("\\d+")

    fun <B> String.paired(sep: String, transform: (String) -> B): Pair<B, B> =
        split(sep).map(transform).let { (a, b) -> a to b }

    fun <A> Regex.findAllByGroup(input: String, group: String, transform: (String) -> A): Sequence<A> =
        findAll(input).mapNotNull { it.groups[group]?.value?.let(transform) }

    fun findAllInts(input: String): List<Int> {
        return intRegex.findAll(input).map { it.value.toInt() }.toList()
    }


    infix fun <A> Set<A>.symmetricDifference(other: Set<A>): Set<A> =
        (this - other) union (other - this)


    /**
     * Returns a new sequence mapping all [A]s to [B]s while threading a state [S] through every
     * transformation.
     *
     * @param initialState the initial state
     * @param transform do the actual transformation
     */
    fun <S, A, B> Sequence<A>.mapAccum(initialState: S, transform: (S, A) -> Pair<S, B>): Sequence<B> = sequence {
        var state = initialState
        for (x in this@mapAccum) {
            val (newState, result) = transform(state, x)
            state = newState
            yield(result)
        }
    }


    /**
     * Returns the input of the given [day] and [year].
     *
     * If the file does not exist, but a .env file exists (please don't commit it),
     * will try to download the input file.
     *
     * @param day the day of the puzzle input
     * @param year the year of the puzzle input
     * @return the input of the puzzle of this day.
     * @throws IllegalArgumentException when the file does not exist and could not be downloaded.
     */
    @Throws(IllegalArgumentException::class)
    fun getInput(day: Int, year: Int = Year.now().value): String {
        val maybeInput = tryDownloadFileIfItDoesNotExist(year, day)
        if (maybeInput != null)
            return maybeInput

        val input = Aoc::class.java.getResourceAsStream("/$year/$day")
            ?: throw IllegalArgumentException("""
                |Sorry, could not automatically download or get the input file for $year/$day: 
                |
                |    https://adventofcode.com/$year/day/$day/input
                |    
                |You might try adding a .env file in your root project directory
                |with the variable SESSION_COOKIE=... (copy it from Firefox). Or
                |just download it.
                |    
                |Happy coding and don't be down, if you don't solve it quickly. 
                |Enjoy the process and learn, what you can. :-)
                |
            """.trimMargin())

        return input.bufferedReader().readText().trimEnd()
    }

    private fun tryDownloadFileIfItDoesNotExist(year: Int, day: Int): String? {
        val envFile = Paths.get(".env")
        val inputFile = Paths.get("src/test/resources/$year/$day")
        if (inputFile.exists() || !envFile.exists()) {
            return null
        }

        val props = Properties().apply { load(envFile.inputStream()) }
        val sessionCookie = props.getProperty("SESSION_COOKIE")
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://adventofcode.com/$year/day/$day/input"))
            .header("Cookie", "session=$sessionCookie")
            .header("User-Agent", "github.com/noobymatze/aoc noobymatze@yahoo.de")
            .GET()
            .build()

        val client = HttpClient.newHttpClient();
        val response = client.send(request, BodyHandlers.ofByteArray())
        if (response.statusCode() != 200) {
            throw RuntimeException("${response.statusCode()} for ${response.uri()}\n\n${response.body()?.toString(Charsets.UTF_8) ?: ""}")
        }

        Files.createDirectories(inputFile.parent)
        val body = response.body()
        Files.write(inputFile, body, StandardOpenOption.CREATE_NEW)
        // We have to return directly here, because the file will
        // not have been put into the build directory, where
        // the actual resources will reside after a build.
        // So for the first run, we just use the response of the server.
        return body.toString(Charsets.UTF_8).trim()
    }

}