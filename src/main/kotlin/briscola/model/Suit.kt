package briscola.model

/**
 * Enum containing the 4 suits.
 */
enum class Suit {
    CLUBS,
    COINS,
    CUPS,
    SWORDS;

    override fun toString(): String {
        return when (this) {
            CLUBS -> "clubs"
            COINS -> "coins"
            CUPS -> "cups"
            SWORDS -> "swords"
        }
    }

    companion object {
        /**
         * Converts a string to a [Suit].
         *
         * @param string the string to convert
         * @return the [Suit] corresponding to the string
         * @throws IllegalArgumentException if the string is not a valid suit
         */
        fun fromString(string: String): Suit {
            return when (string) {
                "clubs" -> CLUBS
                "coins" -> COINS
                "cups" -> CUPS
                "swords" -> SWORDS
                else -> throw IllegalArgumentException("Invalid suit: $string")
            }
        }
    }
}

