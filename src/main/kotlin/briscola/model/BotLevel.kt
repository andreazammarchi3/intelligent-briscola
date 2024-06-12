package briscola.model

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
        fun fromString(string: String?): BotLevel {
            return entries.find { it.toString() == string } ?: STUPID
        }
    }
}