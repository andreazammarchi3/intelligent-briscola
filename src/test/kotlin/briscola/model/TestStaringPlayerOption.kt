package briscola.model

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test [StartingPlayerOption] class.
 */
class TestStaringPlayerOption {
    /**
     * Test toString method.
     */
    @Test
    fun testToString() {
        assertEquals("Random", StartingPlayerOption.RANDOM.toString())
        assertEquals("Player", StartingPlayerOption.PLAYER.toString())
        assertEquals("Bot", StartingPlayerOption.BOT.toString())
    }

    /**
     * Test fromString method.
     */
    @Test
    fun testFromString() {
        assertEquals(StartingPlayerOption.RANDOM, StartingPlayerOption.fromString("Random"))
        assertEquals(StartingPlayerOption.PLAYER, StartingPlayerOption.fromString("Player"))
        assertEquals(StartingPlayerOption.BOT, StartingPlayerOption.fromString("Bot"))
        assertEquals(StartingPlayerOption.RANDOM, StartingPlayerOption.fromString("unknown"))
    }
}