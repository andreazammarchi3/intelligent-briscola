package briscola.model

import kotlin.test.Test
import kotlin.test.*

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
        assertEquals("stupid", endedMatch.botLevel)
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

        val endedMatchWithDifferentPlayerName = EndedMatch("player2", "stupid", "Win", 61, 59)
        assertNotEquals(endedMatch1, endedMatchWithDifferentPlayerName)

        val endedMatchWithDifferentBotLevel = EndedMatch("player1", "smart", "Win", 61, 59)
        assertNotEquals(endedMatch1, endedMatchWithDifferentBotLevel)

        val endedMatchWithDifferentResult = EndedMatch("player1", "stupid", "Lose", 61, 59)
        assertNotEquals(endedMatch1, endedMatchWithDifferentResult)

        val endedMatchWithDifferentPlayerPoints = EndedMatch("player1", "stupid", "Win", 62, 59)
        assertNotEquals(endedMatch1, endedMatchWithDifferentPlayerPoints)

        val endedMatchWithDifferentBotPoints = EndedMatch("player1", "stupid", "Win", 61, 60)
        assertNotEquals(endedMatch1, endedMatchWithDifferentBotPoints)

        assertFalse(endedMatch1.equals("Not a EndedMatch"))

        assertEquals(endedMatch1, endedMatch1)

        assertFalse(endedMatch1.equals( null))
    }

    /**
     * Test the hashCode method of EndedMatch
     */
    @Test
    fun testHashCode() {
        val endedMatch1 = EndedMatch("player1", "stupid", "Win", 61, 59)
        val endedMatch2 = EndedMatch("player1", "stupid", "Win", 61, 59)
        assertEquals(endedMatch1.hashCode(), endedMatch2.hashCode())
    }
}