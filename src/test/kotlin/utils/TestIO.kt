package utils

import briscola.model.EndedMatch
import briscola.utils.IO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for the IO utility class.
 */
class TestIO {
    private val TEST_FILE_PATH = "src/test/resources/scoreboard/endedMatches.json"
    private val gson = Gson()
    private val endedMatch1 = EndedMatch("Pippo", "stupid", "Win", 120, 0)
    private val endedMatch2 = EndedMatch("Pluto", "normal", "Loss", 0, 120)

    /**
     * Test the saveEndedMatch method.
     */
    @Test
    fun testSaveEndedMatch() {
        IO.saveEndedMatch(endedMatch1, test = true)
        IO.saveEndedMatch(endedMatch2, test = true)
        File(TEST_FILE_PATH).reader().use { reader ->
            val fileType = object : TypeToken<List<EndedMatch>>() {}.type
            val fileContent: List<EndedMatch> = gson.fromJson(reader, fileType)
            assertEquals(2, fileContent.size)
            assertEquals(endedMatch1, fileContent[0])
            assertEquals(endedMatch2, fileContent[1])
        }

        // Clean up the test environment
        File(TEST_FILE_PATH).delete()
    }


    /**
     * Test the getEndedMatches method.
     */
    @Test
    fun testGetEndedMatches() {
        // Prepare the test environment by clearing the existing file and writing known test data
        FileWriter(TEST_FILE_PATH).use { writer ->
            val matches = listOf(endedMatch1, endedMatch2)
            val json = gson.toJson(matches)
            writer.write(json)
        }

        // Perform the test
        val retrievedMatches = IO.getEndedMatches(test = true)

        // Assertions to verify the correct data is retrieved
        assertEquals(2, retrievedMatches.size, "Should retrieve exactly two matches")
        assertEquals(endedMatch1.playerName, retrievedMatches[0].playerName)
        assertEquals(endedMatch1.result, retrievedMatches[0].result)
        assertEquals(endedMatch1.playerPoints, retrievedMatches[0].playerPoints)
        assertEquals(endedMatch1.botPoints, retrievedMatches[0].botPoints)

        assertEquals(endedMatch2.playerName, retrievedMatches[1].playerName)
        assertEquals(endedMatch2.result, retrievedMatches[1].result)
        assertEquals(endedMatch2.playerPoints, retrievedMatches[1].playerPoints)
        assertEquals(endedMatch2.botPoints, retrievedMatches[1].botPoints)

        // Clean up the test environment
        File(TEST_FILE_PATH).delete()
    }
}