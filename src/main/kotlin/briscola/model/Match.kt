package briscola.model

import briscola.utils.Math
import kotlin.random.Random

/**
 * Represents a match between two players
 * @param players the players of the match
 * @param deck the deck of the match
 * @property playingFirstPlayer true if the first player is playing, false otherwise
 * @property winner the winner of the match
 * @property lastCard the last card drawn from the deck
 * @property briscolaSuit the briscola suit
 * @property playedCards the cards played in the match
 */
class Match(val players: MutableList<Player>, val deck: MutableList<Card>) {
    private var playingFirstPlayer = true
    private var winner: Player? = null
    private var lastCard: Card? = null
    private var briscolaSuit: Suit? = null
    private val playedCards = mutableListOf<Card>()

    /**
     * Returns true if the first player is playing, false otherwise
     * @return true if the first player is playing, false otherwise
     */
    fun playingFirstPlayer(): Boolean {
        return playingFirstPlayer
    }

    /**
     * Get the winner of the match
     * @return the winner of the match
     */
    fun getWinner(): Player? {
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
    fun prepareMatch(shuffleDeck: Boolean = true, startingPlayerOption: StartingPlayerOption = StartingPlayerOption.RANDOM) {
        if (shuffleDeck) deck.shuffle()

        lastCard = deck.removeAt(0)
        briscolaSuit = lastCard!!.getSuit()

        if ((startingPlayerOption == StartingPlayerOption.RANDOM && Random.nextBoolean()) || startingPlayerOption == StartingPlayerOption.PLAYER2) {
            switchTurn()
        }

        for (i in 0 .. 2) {
            playerDrawCard(players[0])
            playerDrawCard(players[1])
        }
    }

    /**
     * Let the player play a card
     * @param player the player that plays the card
     * @param card the card played by the player
     * @throws IllegalStateException if the player doesn't have the card in hand
     * @throws IllegalStateException if the match ended in a draw
     * @throws IllegalStateException if the player tries to play a card when it's not his turn
     */
    fun playerPlayCard(player: Player, card: Card) {
        if (!player.hasCardInHand(card)) {
            return
        }
        if (playingFirstPlayer) {
            cardPlayedFirst(player, card)
            playingFirstPlayer = false
        } else {
            try {
                cardPlayedSecond(player, card)
                if (winner == null) {
                    playingFirstPlayer = true
                }
            } catch (e: IllegalStateException) {
                println(e.message)
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
        players.forEach { it.reset() }
        deck.clear()
        deck.addAll(Card.entries)
    }

    /**
     * Check if the match has a winner, if so set the winner, otherwise do nothing.
     * If the match ended in a draw, set the winner to a new player with the name "Draw"
     */
    fun checkWinner() {
        winner = if (players[0].getHandCards().isEmpty() && players[1].getHandCards().isEmpty()) {
            if (players[0].points() > players[1].points()) {
                players[0]
            } else if (players[0].points() < players[1].points()) {
                players[1]
            } else {
                Player("Draw")
            }
        } else {
            null
        }
    }

    private fun switchTurn() {
        players.reverse()
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

    private fun cardPlayedFirst(player: Player, card: Card) {
        player.playCard(card)
        playedCards.add(card)
    }

    private fun cardPlayedSecond(player: Player, card: Card) {
        player.playCard(card)
        playedCards.add(card)
        val higherCard = Math.getHigherCard(playedCards[0], playedCards[1], briscolaSuit!!)
        if (higherCard == playedCards[1]) {
            switchTurn()
        }

        playerDrawCard(players[0])
        playerDrawCard(players[1])

        players[0].gainCard(playedCards[0])
        players[0].gainCard(playedCards[1])

        playedCards.clear()
    }
}