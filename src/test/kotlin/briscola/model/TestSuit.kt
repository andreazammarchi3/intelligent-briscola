package briscola.model

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for [Suit].
 */
class TestSuit {
    /**
     * Test the [Suit.toString] method.
     */
    @Test
    fun testToString() {
        assertEquals("clubs", Suit.CLUBS.toString())
        assertEquals("coins", Suit.COINS.toString())
        assertEquals("cups", Suit.CUPS.toString())
        assertEquals("swords", Suit.SWORDS.toString())
    }

    /**
     * Test the [Suit.fromString] method.
     */
    @Test
    fun testFromString() {
        assertEquals(Suit.CLUBS, Suit.fromString("clubs"))
        assertEquals(Suit.COINS, Suit.fromString("coins"))
        assertEquals(Suit.CUPS, Suit.fromString("cups"))
        assertEquals(Suit.SWORDS, Suit.fromString("swords"))
    }
}