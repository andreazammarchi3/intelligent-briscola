package model

import kotlin.test.Test
import kotlin.test.assertEquals

class TestSuit {
    @Test
    fun testToString() {
        assertEquals("Clubs", Suit.CLUBS.toString())
        assertEquals("Coins", Suit.COINS.toString())
        assertEquals("Cups", Suit.CUPS.toString())
        assertEquals("Swords", Suit.SWORDS.toString())
    }
}