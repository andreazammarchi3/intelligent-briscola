package briscola.model

/**
 * Represents a player in the game.
 * @param name The name of the player.
 * @param isBot Whether the player is a bot or not.
 */
open class Player(val name: String,
                  val isBot: Boolean = false,
                  val handCards: MutableList<Card> = mutableListOf(),
                  val gainedCards: MutableList<Card> = mutableListOf()) {

    /**
     * Check if the player has a card.
     * @param card The card to check.
     * @return [Boolean] Whether the player has the card.
     */
    fun hasCardInHand(card: Card): Boolean {
        return handCards.contains(card)
    }

    /**
     * Check if the player has a card in the gained cards.
     * @param card The card to check.
     * @return [Boolean] Whether the player has the card in the gained cards.
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
     * @return The [Card] played.
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
    }

    /**
     * Get the points of the player's gained cards.
     * @return [Int] The points of the player's gained cards.
     */
    fun points(): Int {
        return gainedCards.sumOf {
            it.value
        }
    }

    /**
     * Reset the player's state.
     */
    fun reset() {
        handCards.clear()
        gainedCards.clear()
    }

    override fun toString(): String {
        return "Player(name='$name', isBot=$isBot, handCards=$handCards, gainedCards=$gainedCards)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Player) return false

        if (name != other.name) return false
        if (isBot != other.isBot) return false
        if (handCards != other.handCards) return false
        if (gainedCards != other.gainedCards) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isBot.hashCode()
        result = 31 * result + handCards.hashCode()
        result = 31 * result + gainedCards.hashCode()
        return result
    }
}
