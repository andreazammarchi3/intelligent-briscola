package model

/**
 * Represents a player in the game.
 * @param name The name of the player.
 * @param isBot Whether the player is a bot or not.
 */
class Player(val name: String,
             val isBot: Boolean = false,
             private val handCards: MutableList<Card> = mutableListOf(),
             private val gainedCards: MutableList<Card> = mutableListOf(),
             private var points: Int = 0) {

    /**
     * Get the hand cards of the player.
     * @return The hand cards of the player.
     */
    fun getHandCards(): List<Card> {
        return handCards
    }

    /**
     * Get the gained cards of the player.
     * @return The gained cards of the player.
     */
    fun getGainedCards(): List<Card> {
        return gainedCards
    }

    /**
     * Get the points of the player.
     * @return The points of the player.
     */
    fun getPoints(): Int {
        return points
    }

    /**
     * Check if the player has a card.
     * @param card The card to check.
     * @return Whether the player has the card.
     */
    fun hasCardInHand(card: Card): Boolean {
        return handCards.contains(card)
    }

    /**
     * Check if the player has a card in the gained cards.
     * @param card The card to check.
     * @return Whether the player has the card in the gained cards.
     */
    fun hasCardInGained(card: Card): Boolean {
        return gainedCards.contains(card)
    }

    /**
     * Draw a card to the player's hand.
     * @param card The card to draw.
     * @throws IllegalStateException If the player's hand is full.
     */
    fun drawCard(card: Card) {
        if (handCards.size >= 3) {
            throw IllegalStateException("Hand is full")
        }
        if (hasCardInHand(card)) {
            throw IllegalArgumentException("Player already has the card in hand")
        }
        handCards.add(card)
    }

    /**
     * Play a card from the player's hand.
     * @param card The card to play.
     * @return The card played.
     * @throws IllegalArgumentException If the player does not have the card in hand.
     */
    fun playCard(card: Card): Card {
        if (hasCardInHand(card).not()) {
            throw IllegalArgumentException("Player does not have the card in hand")
        }
        handCards.remove(card)
        return card
    }

    /**
     * Gain a card.
     * @param card The card to gain.
     * @throws IllegalArgumentException If the player already gained the card.
     */
    fun gainCard(card: Card) {
        if (gainedCards.contains(card)) {
            throw IllegalArgumentException("Player already gained the card")
        }
        gainedCards.add(card)
        points += card.getValue()
    }

    /**
     * Add points to the player.
     * @param points The points to add.
     */
    fun addPoints(points: Int) {
        this.points += points
    }

    /**
     * Reset the player's state.
     */
    fun reset() {
        handCards.clear()
        gainedCards.clear()
        points = 0
    }
}
