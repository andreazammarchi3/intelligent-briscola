package model

import briscola.model.BotLevel
import kotlin.test.Test

/**
 * Test the BotLevel class.
 */
class TestBotLevel {
    /**
     * Test the toString method.
     */
    @Test
    fun testToString() {
        assert(BotLevel.STUPID.toString() == "stupid")
        assert(BotLevel.NORMAL.toString() == "normal")
        assert(BotLevel.INTELLIGENT.toString() == "intelligent")
    }

    /**
     * Test the fromString method.
     */
    @Test
    fun testFromString() {
        assert(BotLevel.fromString("stupid") == BotLevel.STUPID)
        assert(BotLevel.fromString("normal") == BotLevel.NORMAL)
        assert(BotLevel.fromString("intelligent") == BotLevel.INTELLIGENT)
    }
}