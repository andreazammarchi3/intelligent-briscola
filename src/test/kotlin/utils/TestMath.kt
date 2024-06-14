package utils

import briscola.model.Card
import briscola.model.Suit
import briscola.utils.getHigherCard
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test the math utility functions.
 */
class TestMath {
    /**
     * Test the getHigherCard function.
     */
    @Test
    fun testGetHigherCard() {
        // Test same suit, higher value
        assertEquals(Card.CLUBS_1, getHigherCard(Card.CLUBS_1, Card.CLUBS_2, Suit.COINS))
        // Test same suit, lower value
        assertEquals(Card.CLUBS_1, getHigherCard(Card.CLUBS_2, Card.CLUBS_1, Suit.COINS))
        // Test same suit, same value, lower rank
        assertEquals(Card.CLUBS_4, getHigherCard(Card.CLUBS_2, Card.CLUBS_4, Suit.COINS))
        // Test same suit, same value, higher rank
        assertEquals(Card.CLUBS_4, getHigherCard(Card.CLUBS_4, Card.CLUBS_2, Suit.COINS))

        // Test different suit, briscola
        assertEquals(Card.COINS_1, getHigherCard(Card.CLUBS_1, Card.COINS_1, Suit.COINS))
        // Test different suit, not briscola
        assertEquals(Card.CLUBS_2, getHigherCard(Card.CLUBS_2, Card.SWORDS_1, Suit.COINS))
    }
}