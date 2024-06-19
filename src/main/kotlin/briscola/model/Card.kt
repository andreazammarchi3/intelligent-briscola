package briscola.model

/**
 * Enum containing all the Cards in a typical deck of Briscola.
 * @param id the id of the card.
 * @param suit the suit of the card.
 * @param rank the rank of the card.
 * @param value the value of the card.
 */
enum class Card(val id: Int, val suit: Suit, val rank: Int, val value: Int) {
    CLUBS_1(1, Suit.CLUBS, 1, 11),
    CLUBS_2(2, Suit.CLUBS, 2, 0),
    CLUBS_3(3, Suit.CLUBS, 3, 10),
    CLUBS_4(4, Suit.CLUBS, 4, 0),
    CLUBS_5(5, Suit.CLUBS, 5, 0),
    CLUBS_6(6, Suit.CLUBS, 6, 0),
    CLUBS_7(7, Suit.CLUBS, 7, 0),
    CLUBS_8(8, Suit.CLUBS, 8, 2),
    CLUBS_9(9, Suit.CLUBS, 9, 3),
    CLUBS_10(10, Suit.CLUBS, 10, 4),

    COINS_1(11, Suit.COINS, 1, 11),
    COINS_2(12, Suit.COINS, 2, 0),
    COINS_3(13, Suit.COINS, 3, 10),
    COINS_4(14, Suit.COINS, 4, 0),
    COINS_5(15, Suit.COINS, 5, 0),
    COINS_6(16, Suit.COINS, 6, 0),
    COINS_7(17, Suit.COINS, 7, 0),
    COINS_8(18, Suit.COINS, 8, 2),
    COINS_9(19, Suit.COINS, 9, 3),
    COINS_10(20, Suit.COINS, 10, 4),

    CUPS_1(21, Suit.CUPS, 1, 11),
    CUPS_2(22, Suit.CUPS, 2, 0),
    CUPS_3(23, Suit.CUPS, 3, 10),
    CUPS_4(24, Suit.CUPS, 4, 0),
    CUPS_5(25, Suit.CUPS, 5, 0),
    CUPS_6(26, Suit.CUPS, 6, 0),
    CUPS_7(27, Suit.CUPS, 7, 0),
    CUPS_8(28, Suit.CUPS, 8, 2),
    CUPS_9(29, Suit.CUPS, 9, 3),
    CUPS_10(30, Suit.CUPS, 10, 4),

    SWORDS_1(31, Suit.SWORDS, 1, 11),
    SWORDS_2(32, Suit.SWORDS, 2, 0),
    SWORDS_3(33, Suit.SWORDS, 3, 10),
    SWORDS_4(34, Suit.SWORDS, 4, 0),
    SWORDS_5(35, Suit.SWORDS, 5, 0),
    SWORDS_6(36, Suit.SWORDS, 6, 0),
    SWORDS_7(37, Suit.SWORDS, 7, 0),
    SWORDS_8(38, Suit.SWORDS, 8, 2),
    SWORDS_9(39, Suit.SWORDS, 9, 3),
    SWORDS_10(40, Suit.SWORDS, 10, 4);

    override fun toString(): String {
        return when (rank) {
            1 -> "Ace of $suit"
            8 -> "Jack of $suit"
            9 -> "Horse of $suit"
            10 -> "King of $suit"
            else -> "$rank of $suit"
        }
    }

    companion object {
        /**
         * Get the card from the given id.
         * @param id the id of the card.
         * @return the [Card] with the given id.
         * @throws IllegalArgumentException if no card with the given id is found.
         */
        fun getCardFromId(id: Int): Card {
            for (card in entries) {
                if (card.id == id) {
                    return card
                }
            }
            throw IllegalArgumentException("No card with id $id found")
        }
    }
}