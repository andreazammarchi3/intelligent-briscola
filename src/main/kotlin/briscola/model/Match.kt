package briscola.model

import briscola.utils.IO
import briscola.utils.getHigherCard
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
class Match(val player: Player, val deck: MutableList<Card>, val botLevel: BotLevel) {
    var playerTurn = true
    var winner: Winner? = null
    var lastCard: Card? = null
    var briscolaSuit: Suit? = null
    val playedCards = mutableListOf<Card>()

    var bot = Player("Bot", isBot = true)

    /**
     * Prepare the match by shuffling the deck, setting the last card and giving 3 cards to each player.
     * @param shuffleDeck true if the deck should be shuffled, false otherwise, default is true
     * @param startingPlayerOption the starting player option, default is random
     */
    fun prepareMatch(shuffleDeck: Boolean = true,
                     startingPlayerOption: StartingPlayerOption = StartingPlayerOption.RANDOM) {
        if (shuffleDeck) deck.shuffle()

        lastCard = deck.removeAt(0)
        briscolaSuit = lastCard!!.suit

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
     * Let a player play a card
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
                val result = when (winner) {
                    Winner.PLAYER -> "Win"
                    Winner.BOT -> "Loss"
                    Winner.DRAW -> "Draw"
                    null -> "Unknown"
                }
                IO.saveEndedMatch(EndedMatch(this.player.name, botLevel.toString(),
                    result,
                    this.player.points(),
                    bot.points()))
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
     * The winner is the player with the most points when both players have no cards left in their hand.
     * If the points are equal, the match is a draw.
     */
    fun checkWinner() {
        winner = if (player.handCards.isEmpty() && bot.handCards.isEmpty()) {
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
        return "Match(player=$player, " +
                "playerTurn=$playerTurn, " +
                "winner=$winner, " +
                "lastCard=$lastCard, " +
                "briscolaSuit=$briscolaSuit, " +
                "playedCards=$playedCards)"
    }

    /**
     * A player draws a card from the deck.
     * If the deck is empty, the player draws the last card.
     * @param player the player that draws the card
     */
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

    /**
     * A player plays a card as the first card of the turn.
     * @param currentPlayer the player that plays the card
     * @param card the card played by the player
     */
    private fun cardPlayedFirst(currentPlayer: Player, card: Card) {
        currentPlayer.playCard(card)
        playedCards.add(card)
        playerTurn = !playerTurn
    }

    /**
     * A player plays a card as the second card of the turn.
     * @param currentPlayer the player that plays the card
     * @param card the card played by the player
     */
    private fun cardPlayedSecond(currentPlayer: Player, card: Card) {
        currentPlayer.playCard(card)
        playedCards.add(card)
        val higherCard = getHigherCard(playedCards[0], playedCards[1], briscolaSuit!!)
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