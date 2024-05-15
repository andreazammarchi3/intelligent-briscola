package model

import kotlin.test.Test
import kotlin.test.assertEquals

class TestSuits {
    @Test
    fun testToString() {
        assertEquals("Clubs", Suits.CLUBS.toString())
        assertEquals("Coins", Suits.COINS.toString())
        assertEquals("Cups", Suits.CUPS.toString())
        assertEquals("Swords", Suits.SWORDS.toString())
    }
}