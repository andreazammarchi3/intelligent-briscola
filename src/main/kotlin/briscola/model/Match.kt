package briscola.model

import briscola.utils.Math
import kotlin.random.Random

/**
 * Represents a match between two players
 * @param player the player of the match
 * @param deck the deck of the match
 * @property playerTurn true if the player is playing, false if it's bot's turn
 * @property winner the winner of the match
 * @property lastCard the last card drawn from the deck
 * @property briscolaSuit the briscola suit
 * @property playedCards the cards played in the match
 */
class Match(val player: Player, val deck: MutableList<Card>) {
    private var playerTurn = true
    private var winner: Winner? = null
    private var lastCard: Card? = null
    private var briscolaSuit: Suit? = null
    private val playedCards = mutableListOf<Card>()

    val bot = Bot()

    /**
     * Check if it's player's turn
     * @return true if it's player's turn, false otherwise
     */
    fun isPlayerTurn(): Boolean {
        return playerTurn
    }

    /**
     * Get the winner of the match
     * @return the winner of the match
     */
    fun getWinner(): Winner? {
        return winner
    }

    /**
     * Get the last card drawn from the deck
     * @return the last card drawn from the deck
     */
    fun getLastCard(): Card? {
        return lastCard
    }

    /**
     * Get the briscola suit
     */
    fun getBriscolaSuit(): Suit? {
        return briscolaSuit
    }

    /**
     * Get the cards played in the match
     */
    fun getPlayedCards(): List<Card> {
        return playedCards
    }

    /**
     * Prepare the match by shuffling the deck, setting the last card and giving 3 cards to each player
     * @param shuffleDeck true if the deck should be shuffled, false otherwise, default is true
     * @param startingPlayerOption the starting player option, default is random
     */
    fun prepareMatch(shuffleDeck: Boolean = true, startingPlayerOption: StartingPlayerOption = StartingPlayerOption.PLAYER) {
        if (shuffleDeck) deck.shuffle()

        lastCard = deck.removeAt(0)
        briscolaSuit = lastCard!!.getSuit()

        playerTurn = if (startingPlayerOption == StartingPlayerOption.RANDOM) {
            Random.nextBoolean()
        } else {
            startingPlayerOption == StartingPlayerOption.PLAYER
        }

        for (i in 0 .. 2) {
            playerDrawCard(player)
            playerDrawCard(bot)
        }
    }

    /**
     * Let the player play a card
     * @param player the player that plays the card
     * @param card the card played by the player
     */
    fun playCard(player: Player, card: Card) {
        if (!player.hasCardInHand(card)) {
            return
        }
        if (playedCards.size == 0) {
            cardPlayedFirst(player, card)
        } else {
            cardPlayedSecond(player, card)
            checkWinner()
            if (winner != null) {
                println("The winner is $winner")
            }
        }
    }

    /**
     * Reset the match
     */
    fun reset() {
        winner = null
        lastCard = null
        briscolaSuit = null
        playedCards.clear()
        player.reset()
        bot.reset()
        deck.clear()
        deck.addAll(Card.entries)
    }

    /**
     * Check if the match has a winner, if so set the winner, otherwise do nothing.
     * If the match ended in a draw, set the winner to a new player with the name "Draw"
     */
    fun checkWinner() {
        winner = if (player.getHandCards().isEmpty() && bot.getHandCards().isEmpty()) {
            if (player.points() > bot.points()) {
                Winner.PLAYER
            } else if (player.points() < bot.points()) {
                Winner.BOT
            } else {
                Winner.DRAW
            }
        } else {
            null
        }
    }

    override fun toString(): String {
        return "Match(player=$player, playerTurn=$playerTurn, winner=$winner, lastCard=$lastCard, briscolaSuit=$briscolaSuit, playedCards=$playedCards)"
    }

    private fun playerDrawCard(player: Player) {
        if (deck.isEmpty()) {
            if (lastCard != null) {
                player.drawCard(lastCard!!)
                lastCard = null
            }
            return
        }
        player.drawCard(deck.removeAt(0))
    }

    private fun cardPlayedFirst(currentPlayer: Player, card: Card) {
        currentPlayer.playCard(card)
        playedCards.add(card)
        playerTurn = !playerTurn
    }

    private fun cardPlayedSecond(currentPlayer: Player, card: Card) {
        currentPlayer.playCard(card)
        playedCards.add(card)
        val higherCard = Math.getHigherCard(playedCards[0], playedCards[1], briscolaSuit!!)
        if ((higherCard == playedCards[1] && playerTurn) || (higherCard == playedCards[0] && !playerTurn)) {
            playerTurn = true
            playerDrawCard(player)
            playerDrawCard(bot)
            player.gainCard(playedCards[0])
            player.gainCard(playedCards[1])
        } else {
            playerTurn = false
            playerDrawCard(bot)
            playerDrawCard(player)
            bot.gainCard(playedCards[0])
            bot.gainCard(playedCards[1])
        }

        playedCards.clear()
    }
}