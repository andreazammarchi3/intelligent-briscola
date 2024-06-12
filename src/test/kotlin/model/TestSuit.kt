package model

import briscola.model.Suit
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSuit {
    @Test
    fun testToString() {
        assertEquals("clubs", Suit.CLUBS.toString())
        assertEquals("coins", Suit.COINS.toString())
        assertEquals("cups", Suit.CUPS.toString())
        assertEquals("swords", Suit.SWORDS.toString())
    }
}