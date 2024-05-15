package model

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
            CLUBS -> "Clubs"
            COINS -> "Coins"
            CUPS -> "Cups"
            SWORDS -> "Swords"
        }
    }
}

