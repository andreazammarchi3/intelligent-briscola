package model

import briscola.model.Card
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCard {
    @Test
    fun testGetId() {
        for (card in Card.entries) {
            assertEquals(card.ordinal + 1, card.getId())
        }
    }

    @Test
    fun testGetSuit() {
        for (card in Card.entries) {
            assertEquals(card.ordinal / 10, card.getSuit().ordinal)
        }
    }

    @Test
    fun testGetRank() {
        for (card in Card.entries) {
                assertEquals(card.ordinal % 10 + 1, card.getRank())
        }
    }

    @Test
    fun testGetValue() {
        for (card in Card.entries) {
            when (card.getRank()) {
                1 -> assertEquals(11, card.getValue())
                3 -> assertEquals(10, card.getValue())
                8 -> assertEquals(2, card.getValue())
                9 -> assertEquals(3, card.getValue())
                10 -> assertEquals(4, card.getValue())
                else -> assertEquals(0, card.getValue())
            }
        }
    }

    @Test
    fun testGetCardById() {
        for (card in Card.entries) {
            assertEquals(card, card.getCardById(card.getId()))
        }
    }

    @Test
    fun testToString() {
        for (card in Card.entries) {
            when (card.getRank()) {
                1 -> assertEquals("Ace of ${card.getSuit()}", card.toString())
                8 -> assertEquals("Jack of ${card.getSuit()}", card.toString())
                9 -> assertEquals("Horse of ${card.getSuit()}", card.toString())
                10 -> assertEquals("King of ${card.getSuit()}", card.toString())
                else -> assertEquals("${card.getRank()} of ${card.getSuit()}", card.toString())
            }
        }
    }
}