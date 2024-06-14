package briscola.model

/**
 * Enum class representing the bot level.
 * The bot level can be:
 * - STUPID: the bot plays randomly
 * - NORMAL: the bot plays with a simple strategy
 * - INTELLIGENT: the bot plays with a more complex strategy
 */
enum class BotLevel {
    STUPID, NORMAL, INTELLIGENT;

    override fun toString(): String {
        return when (this) {
            STUPID -> "stupid"
            NORMAL -> "normal"
            INTELLIGENT -> "intelligent"
        }
    }

    companion object {
        /**
         * Get the corresponding BotLevel enum value from a string.
         * If the string does not correspond to any BotLevel value, the default value is STUPID.
         * @param string the string to convert
         * @return the corresponding [BotLevel] value
         */
        fun fromString(string: String?): BotLevel {
            return entries.find { it.toString() == string } ?: STUPID
        }
    }
}