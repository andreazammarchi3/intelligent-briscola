package briscola.model

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for EndedMatch
 */
class TestEndedMatch {
    /**
     * Test the properties of EndedMatch
     */
    @Test
    fun testEndedMatch() {
        val endedMatch = EndedMatch("player1", "stupid", "Win", 61, 59)
        assertEquals("player1", endedMatch.playerName)
        assertEquals("Win", endedMatch.result)
        assertEquals(61, endedMatch.playerPoints)
        assertEquals(59, endedMatch.botPoints)
    }

    /**
     * Test the equals method of EndedMatch
     */
    @Test
    fun testEquals() {
        val endedMatch1 = EndedMatch("player1", "stupid", "Win", 61, 59)
        val endedMatch2 = EndedMatch("player1", "stupid", "Win", 61, 59)
        assertEquals(endedMatch1, endedMatch2)
    }
}