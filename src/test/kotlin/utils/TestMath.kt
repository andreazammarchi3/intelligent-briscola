package utils

import briscola.model.Card
import briscola.model.Suit
import briscola.utils.Math
import kotlin.test.Test
import kotlin.test.assertEquals

class TestMath {
    @Test
    fun testGetHigherCard() {
        // Test same suit
        val card1 = Card.CLUBS_1
        val card2 = Card.CLUBS_2
        assertEquals(card1, Math.getHigherCard(card1, card2, Suit.COINS))

        // Test same value
        val card3 = Card.CLUBS_2
        assertEquals(card2, Math.getHigherCard(card2, card3, Suit.COINS))

        // Test same rank
        val card4 = Card.COINS_2
        assertEquals(card4, Math.getHigherCard(card2, card4, Suit.COINS))

        // Test briscola
        val card5 = Card.COINS_3
        assertEquals(card5, Math.getHigherCard(card4, card5, Suit.COINS))

        // Test briscola and different suit
        val card6 = Card.CLUBS_3
        assertEquals(card5, Math.getHigherCard(card6, card5, Suit.COINS))
    }
}