package briscola.model

class Bot: Player("Bot", true) {
    /**
     * Play a card from the bot's hand.
     * @return The card played by the bot.
     */
    fun playCard(): Card {
        // Randomly play a card
        val card = getHandCards().random()
        return card
    }
}