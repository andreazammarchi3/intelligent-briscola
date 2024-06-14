package briscola.model

/**
 * Enum class representing the possible options for the starting player.
 * - RANDOM: the starting player is randomly chosen.
 * - PLAYER: the starting player is the human player.
 * - PLAYER2: the starting player is the bot.
 */
enum class StartingPlayerOption {
    RANDOM, PLAYER, BOT;

    override fun toString(): String {
        return when (this) {
            RANDOM -> "Random"
            PLAYER -> "Player"
            BOT -> "Bot"
        }
    }

    companion object {
        /**
         * Get the corresponding [StartingPlayerOption] from a string.
         * @param string the string to convert
         * @return the corresponding [StartingPlayerOption]
         */
        fun fromString(string: String?): StartingPlayerOption {
            return entries.find { it.toString() == string } ?: RANDOM
        }
    }
}