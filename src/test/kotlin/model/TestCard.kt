package model

import briscola.model.Card
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Test class for the Card class.
 */
class TestCard {
    /**
     * Test the correct association of the card rank to its value.
     */
    @Test
    fun testValue() {
        for (card in Card.entries) {
            when (card.rank) {
                1 -> assertEquals(11, card.value)
                3 -> assertEquals(10, card.value)
                8 -> assertEquals(2, card.value)
                9 -> assertEquals(3, card.value)
                10 -> assertEquals(4, card.value)
                else -> assertEquals(0, card.value)
            }
        }
    }

    /**
     * Test the correct retrieval of a card by its id.
     */
    @Test
    fun testGetCardById() {
        for (card in Card.entries) {
            assertEquals(card, Card.getCardFromId(card.id))
        }
    }

    /**
     * Test method toString.
     */
    @Test
    fun testToString() {
        for (card in Card.entries) {
            when (card.rank) {
                1 -> assertEquals("Ace of ${card.suit}", card.toString())
                8 -> assertEquals("Jack of ${card.suit}", card.toString())
                9 -> assertEquals("Horse of ${card.suit}", card.toString())
                10 -> assertEquals("King of ${card.suit}", card.toString())
                else -> assertEquals("${card.rank} of ${card.suit}", card.toString())
            }
        }
    }
}