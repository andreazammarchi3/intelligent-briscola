package model

import briscola.model.Card
import briscola.model.Player
import kotlin.test.*

/**
 * Test class for the Player class.
 */
class TestPlayer {
    private val handCard1: Card = Card.CUPS_1
    private val handCard2: Card = Card.CLUBS_2
    private val gainedCard1: Card = Card.COINS_4
    private val gainedCard2: Card = Card.CUPS_5
    private val player: Player = Player(
        "Player",
        false,
        mutableListOf(handCard1, handCard2),
        mutableListOf(gainedCard1, gainedCard2))

    /**
     * Test if the player has a card in hand.
     */
    @Test
    fun testHasCardInHand() {
        assertEquals(true, player.hasCardInHand(handCard1))
        assertEquals(false, player.hasCardInHand(gainedCard1))
    }

    /**
     * Test if the player has a card in gained cards.
     */
    @Test
    fun testHasCardInGained() {
        assertEquals(true, player.hasCardInGained(gainedCard1))
        assertEquals(false, player.hasCardInGained(handCard1))
    }

    /**
     * Test draw card method.
     */
    @Test
    fun testDrawCard() {
        assertFailsWith<IllegalArgumentException>(message = "Player already has the card in hand") {
            player.drawCard(handCard1)
        }

        val newCard: Card = Card.CLUBS_3
        player.drawCard(newCard)
        assertEquals(3, player.handCards.size)
        assertTrue(player.hasCardInHand(newCard))

        assertFailsWith<IllegalStateException> (message = "Hand is full") {
            player.drawCard(Card.COINS_6)
        }
    }

    /**
     * Test play card method.
     */
    @Test
    fun testPlayCard() {
        player.playCard(handCard1)
        assertEquals(1, player.handCards.size)
        assertFalse(player.hasCardInHand(handCard1))

        assertFailsWith<IllegalArgumentException>(message = "Player does not have the card in hand") {
            player.playCard(handCard1)
        }
    }

    /**
     * Test gain card method.
     */
    @Test
    fun testGainCard() {
        assertFailsWith<IllegalArgumentException>(message = "Player already has the card in gained cards") {
            player.gainCard(gainedCard1)
        }
        val card: Card = Card.COINS_6
        player.gainCard(card)
        assertEquals(3, player.gainedCards.size)
        assertTrue(player.hasCardInGained(card))
    }

    /**
     * Test points method.
     */
    @Test
    fun testPoints() {
        var points = 0
        for (card in player.gainedCards) {
            points += card.value
        }
        assertEquals(points, player.points())
    }

    /**
     * Test reset method.
     */
    @Test
    fun testReset() {
        player.reset()
        assertEquals(0, player.handCards.size)
        assertEquals(0, player.gainedCards.size)
    }

    /**
     * Test toString method.
     */
    @Test
    fun testToString() {
        assertEquals("Player(name='" + player.name + "', isBot=" + player.isBot + ", handCards=" +
                player.handCards + ", gainedCards=" + player.gainedCards + ")", player.toString())
    }

    /**
     * Test equals method.
     */
    @Test
    fun testEquals() {
        val player2 = Player(
            "Player",
            false,
            mutableListOf(handCard1, handCard2),
            mutableListOf(gainedCard1, gainedCard2))
        assertEquals(player, player2)
    }

}