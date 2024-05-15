package model

/**
 * Enum containing the 4 suits.
 */
enum class Suits {
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

