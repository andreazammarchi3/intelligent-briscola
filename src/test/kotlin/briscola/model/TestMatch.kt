package briscola.model

import briscola.utils.getHigherCard
import kotlin.test.*

/**
 * Test class for [Match].
 */
class TestMatch {
    private val player = Player("Player")
    private val match = Match(player, Card.entries.toMutableList(), BotLevel.STUPID)

    /**
     * Test the constructor of [Match].
     * It should initialize the match with the given player, deck, and bot level.
     */
    @Test
    fun testInit() {
        assertEquals(player, match.player)
        assertEquals(40, match.deck.size)
        assertEquals(0, match.playedCards.size)
        assertNull(match.winner)
        assertNull(match.lastCard)
        assertNull(match.briscolaSuit)
        assertTrue(match.playerTurn)
        assertNull(match.winner)
        assertEquals(BotLevel.STUPID, match.botLevel)

        val bot = Player("Bot", isBot = true)
        match.bot = bot
        assertEquals(bot, match.bot)

        val playerTurn = false
        match.playerTurn = playerTurn
        assertFalse(match.playerTurn)

        val winner = Winner.BOT
        match.winner = winner
        assertEquals(winner, match.winner)

        val lastCard = Card.SWORDS_1
        match.lastCard = lastCard
        assertEquals(lastCard, match.lastCard)

        val briscolaSuit = Suit.SWORDS
        match.briscolaSuit = briscolaSuit
        assertEquals(briscolaSuit, match.briscolaSuit)
    }

    /**
     * Test the [Match.prepareMatch] method.
     * It should prepare the match by shuffling the deck, setting the starting player, and dealing the cards.
     */
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

    /**
     * Test the [Match.playCard] method.
     * It should play the given card and update the match state accordingly.
     */
    @Test
    fun testPlayCard() {
        match.prepareMatch(startingPlayerOption = StartingPlayerOption.PLAYER)

        val card1 = match.player.handCards[0]
        val tmpPlayerTurn = match.playerTurn
        match.playCard(match.player, card1)
        assertEquals(card1, match.playedCards[0])
        assertNotEquals(tmpPlayerTurn, match.playerTurn)

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

        // test a little match where players have to draw all the cards
        player.reset()
        val smallMatch = Match(player, Card.entries.subList(0, 8).toMutableList(), BotLevel.STUPID)
        smallMatch.prepareMatch(startingPlayerOption = StartingPlayerOption.PLAYER)
        smallMatch.playCard(smallMatch.player, smallMatch.player.handCards[0])
        smallMatch.playCard(smallMatch.bot, smallMatch.bot.handCards[0])
        assertNull(smallMatch.lastCard)
        for (i in 1..3) {
            smallMatch.playCard(smallMatch.player, smallMatch.player.handCards[0])
            smallMatch.playCard(smallMatch.bot, smallMatch.bot.handCards[0])
        }
        smallMatch.playCard(player, Card.SWORDS_1)
        assertNotNull(smallMatch.winner)
    }

    /**
     * Test the [Match.reset] method.
     */
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

    /**
     * Test the [Match.toString] method.
     */
    @Test
    fun testToString() {
        val expected = "Match(player=Player(name='Player', isBot=false, handCards=[], gainedCards=[])," +
                " playerTurn=true, winner=null, lastCard=null, briscolaSuit=null, playedCards=[])"
        assertEquals(expected, match.toString())
    }
}