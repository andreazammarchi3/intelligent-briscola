package briscola.model

import briscola.utils.getHigherCard
import kotlin.test.*

class TestMatch {
    private val player = Player("Player")
    private val match = Match(player, Card.entries.toMutableList(), BotLevel.STUPID)

    @Test
    fun testInit() {
        assertEquals(player, match.player)
        assertEquals(40, match.deck.size)
        assertEquals(0, match.playedCards.size)
        assertNull(match.winner)
        assertNull(match.lastCard)
        assertNull(match.briscolaSuit)
    }

    @Test
    fun testPrepareMatch() {
        match.prepareMatch(shuffleDeck = false, startingPlayerOption = StartingPlayerOption.BOT)
        val unshuffledDeck = Card.entries.toMutableList()
        for (i in 0..6) {
            unshuffledDeck.removeAt(0)
        }
        assertEquals(unshuffledDeck, match.deck)
        assertFalse(match.playerTurn)

        match.reset()
        match.prepareMatch()
        assertEquals(33, match.deck.size)
        assertEquals(0, match.playedCards.size)
        assertNotNull(match.lastCard)
        assertNotNull(match.briscolaSuit)
        assertEquals(3, match.player.handCards.size)
        assertEquals(3, match.bot.handCards.size)
    }

    @Test
    fun testPlayCard() {
        match.prepareMatch(startingPlayerOption = StartingPlayerOption.PLAYER)

        val card1 = match.player.handCards[0]
        match.playCard(match.player, card1)
        assertEquals(card1, match.playedCards[0])

        val card2 = match.bot.handCards[0]
        match.playCard(match.bot, card2)
        if (getHigherCard(card1, card2, match.briscolaSuit!!) == card1) {
            assertTrue(match.player.gainedCards.isNotEmpty())
            assertTrue(match.bot.gainedCards.isEmpty())
        } else {
            assertTrue(match.player.gainedCards.isEmpty())
            assertTrue(match.bot.gainedCards.isNotEmpty())
        }
        assertEquals(3, match.player.handCards.size)
        assertEquals(3, match.bot.handCards.size)
    }

    @Test
    fun testCheckWinner() {
        match.player.gainCard(Card.SWORDS_1)
        match.bot.gainCard(Card.SWORDS_3)
        match.deck.clear()
        match.checkWinner()
        assertEquals(Winner.PLAYER, match.winner)

        match.player.gainCard(Card.SWORDS_2)
        match.bot.gainCard(Card.SWORDS_10)
        match.checkWinner()
        assertEquals(Winner.BOT, match.winner)

        match.player.gainCard(Card.SWORDS_9)
        match.checkWinner()
        assertEquals(Winner.DRAW, match.winner)
    }
}