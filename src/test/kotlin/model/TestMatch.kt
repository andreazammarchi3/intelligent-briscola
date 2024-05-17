package model

import briscola.model.*
import briscola.utils.Math
import kotlin.test.*

class TestMatch {
    private val player = Player("Player")
    private val match = Match(player, Card.entries.toMutableList())

    @Test
    fun testInit() {
        assertEquals(player, match.player)
        assertEquals(40, match.deck.size)
        assertEquals(0, match.getPlayedCards().size)
        assertNull(match.getWinner())
        assertNull(match.getLastCard())
        assertNull(match.getBriscolaSuit())
    }

    @Test
    fun testIsPlayerTurn() {
        assertTrue(match.isPlayerTurn())
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
        match.prepareMatch(shuffleDeck = false, startingPlayerOption = StartingPlayerOption.BOT)
        val unshuffledDeck = Card.entries.toMutableList()
        for (i in 0..6) {
            unshuffledDeck.removeAt(0)
        }
        assertEquals(unshuffledDeck, match.deck)
        assertFalse(match.isPlayerTurn())

        match.reset()
        match.prepareMatch()
        assertEquals(33, match.deck.size)
        assertEquals(0, match.getPlayedCards().size)
        assertNotNull(match.getLastCard())
        assertNotNull(match.getBriscolaSuit())
        assertEquals(3, match.player.getHandCards().size)
        assertEquals(3, match.bot.getHandCards().size)
    }

    @Test
    fun testPlayCard() {
        match.prepareMatch()

        val card1 = match.player.getHandCards()[0]
        match.playCard(match.player, card1)
        assertEquals(card1, match.getPlayedCards()[0])

        val card2 = match.bot.getHandCards()[0]
        match.playCard(match.bot, card2)
        println(card1)
        println(card2)
        println(match.getPlayedCards())
        println(match.player.getGainedCards())
        println(match.bot.getGainedCards())
        if (Math.getHigherCard(card1, card2, match.getBriscolaSuit()!!) == card1) {
            assertTrue(match.player.getGainedCards().isNotEmpty())
            assertTrue(match.bot.getGainedCards().isEmpty())
        }
        assertEquals(3, match.player.getHandCards().size)
        assertEquals(3, match.bot.getHandCards().size)
    }

    @Test
    fun testCheckWinner() {
        match.player.gainCard(Card.SWORDS_1)
        match.bot.gainCard(Card.SWORDS_3)
        match.deck.clear()
        match.checkWinner()
        assertEquals(Winner.PLAYER, match.getWinner())

        match.player.gainCard(Card.SWORDS_2)
        match.bot.gainCard(Card.SWORDS_10)
        match.checkWinner()
        assertEquals(Winner.BOT, match.getWinner())

        match.player.gainCard(Card.SWORDS_9)
        match.checkWinner()
        assertEquals(Winner.DRAW, match.getWinner())
    }
}