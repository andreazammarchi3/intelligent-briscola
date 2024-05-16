package model

import briscola.model.StartingPLayerOption
import utils.Math
import kotlin.test.*

class TestMatch {
    private val player1 = Player("Player 1")
    private val player2 = Player("Player 2")
    private val match = Match(mutableListOf(player1, player2), Card.entries.toMutableList())

    @Test
    fun testInit() {
        assertEquals(player1, match.players[0])
        assertEquals(player2, match.players[1])
        assertEquals(40, match.deck.size)
        assertEquals(0, match.getPlayedCards().size)
        assertNull(match.getWinner())
        assertNull(match.getLastCard())
        assertNull(match.getBriscolaSuit())
    }

    @Test
    fun testPlayingFirstPlayer() {
        assertTrue(match.playingFirstPlayer())
    }

    @Test
    fun testGetWinner() {
        assertNull(match.getWinner())
    }

    @Test
    fun testGetLastCard() {
        assertNull(match.getLastCard())
    }

    @Test
    fun testGetBriscolaSuit() {
        assertNull(match.getBriscolaSuit())
    }

    @Test
    fun testGetPlayedCards() {
        assertEquals(0, match.getPlayedCards().size)
    }

    @Test
    fun testPrepareMatch() {
        match.prepareMatch()
        assertEquals(33, match.deck.size)
        assertEquals(0, match.getPlayedCards().size)
        assertNotNull(match.getLastCard())
        assertNotNull(match.getBriscolaSuit())
        assertEquals(3, player1.getHandCards().size)
        assertEquals(3, player2.getHandCards().size)

        match.reset()
        match.prepareMatch(shuffleDeck = false, startingPlayerOption = StartingPLayerOption.PLAYER2)
        val unshuffledDeck = Card.entries.toMutableList()
        for (i in 0..6) {
            unshuffledDeck.removeAt(0)
        }
        assertEquals(unshuffledDeck, match.deck)
        assertEquals(player2, match.players[0])
        assertEquals(player1, match.players[1])
    }

    @Test
    fun testPlayerPlayCard() {
        match.prepareMatch()

        val card1 = match.players[0].getHandCards()[0]
        match.playerPlayCard(match.players[0], card1)
        assertEquals(card1, match.getPlayedCards()[0])

        val card2 = match.players[1].getHandCards()[0]
        match.playerPlayCard(match.players[1], card2)
        println(card1)
        println(card2)
        println(match.getPlayedCards())
        println(match.players[0].getGainedCards())
        println(match.players[1].getGainedCards())
        if (Math.getHigherCard(card1, card2, match.getBriscolaSuit()!!) == card1) {
            assertTrue(match.players[0].getGainedCards().isNotEmpty())
            assertTrue(match.players[1].getGainedCards().isEmpty())
        }
        assertEquals(3, match.players[0].getHandCards().size)
        assertEquals(3, match.players[1].getHandCards().size)
    }
}