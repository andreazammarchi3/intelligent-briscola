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

