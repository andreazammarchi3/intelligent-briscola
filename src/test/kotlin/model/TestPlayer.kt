package model

import kotlin.test.*

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

    @Test
    fun testGetHandCards() {
        assertEquals(2, player.getHandCards().size)
    }

    @Test
    fun testGetGainedCards() {
        assertEquals(2, player.getGainedCards().size)
    }

    @Test
    fun testHasCardInHand() {
        assertEquals(true, player.hasCardInHand(handCard1))
        assertEquals(false, player.hasCardInHand(gainedCard1))
    }

    @Test
    fun testHasCardInGained() {
        assertEquals(true, player.hasCardInGained(gainedCard1))
        assertEquals(false, player.hasCardInGained(handCard1))
    }

    @Test
    fun testDrawCard() {
        assertFailsWith<IllegalArgumentException>(message = "Player already has the card in hand") {
            player.drawCard(handCard1)
        }

        val newCard: Card = Card.CLUBS_3
        player.drawCard(newCard)
        assertEquals(3, player.getHandCards().size)
        assertTrue(player.hasCardInHand(newCard))

        assertFailsWith<IllegalStateException> (message = "Hand is full") {
            player.drawCard(Card.COINS_6)
        }
    }

    @Test
    fun testPlayCard() {
        player.playCard(handCard1)
        assertEquals(1, player.getHandCards().size)
        assertFalse(player.hasCardInHand(handCard1))

        assertFailsWith<IllegalArgumentException>(message = "Player does not have the card in hand") {
            player.playCard(handCard1)
        }
    }

    @Test
    fun testGainCard() {
        assertFailsWith<IllegalArgumentException>(message = "Player already has the card in gained cards") {
            player.gainCard(gainedCard1)
        }
        val card: Card = Card.COINS_6
        player.gainCard(card)
        assertEquals(3, player.getGainedCards().size)
        assertTrue(player.hasCardInGained(card))
    }

    @Test
    fun testPoints() {
        var points = 0
        for (card in player.getGainedCards()) {
            points += card.getValue()
        }
        assertEquals(points, player.points())
    }

    @Test
    fun testReset() {
        player.reset()
        assertEquals(0, player.getHandCards().size)
        assertEquals(0, player.getGainedCards().size)
    }

    @Test
    fun testToString() {
        assertEquals("Player(name='" + player.name + "', isBot=" + player.isBot + ", handCards=" +
                player.getHandCards() + ", gainedCards=" + player.getGainedCards() + ")", player.toString())
    }

    @Test
    fun testEquals() {
        val player2: Player = Player(
            "Player",
            false,
            mutableListOf(handCard1, handCard2),
            mutableListOf(gainedCard1, gainedCard2))
        assertEquals(player, player2)
    }

}