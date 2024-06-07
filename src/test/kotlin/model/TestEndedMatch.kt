package model

import briscola.model.EndedMatch
import kotlin.test.Test
import kotlin.test.assertEquals

class TestEndedMatch {
    @Test
    fun testEndedMatch() {
        val endedMatch = EndedMatch("player1", "Win", 61, 59)
        assertEquals("player1", endedMatch.playerName)
        assertEquals("Win", endedMatch.result)
        assertEquals(61, endedMatch.playerPoints)
        assertEquals(59, endedMatch.botPoints)
    }

    @Test

}