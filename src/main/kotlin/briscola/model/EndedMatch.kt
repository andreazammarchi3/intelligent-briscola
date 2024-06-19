package briscola.model

/**
 * Represents a match that has ended.
 * This class is used to store the information of a match that has ended.
 * Useful for the scoreboard.
 * @property playerName the name of the player
 * @property botLevel the level of the bot
 * @property result the result of the match
 * @property playerPoints the points of the player
 * @property botPoints the points of the bot
 */
class EndedMatch(val playerName: String,
                 val botLevel: String,
                 val result: String,
                 val playerPoints: Int,
                 val botPoints: Int) {

    @Override
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as EndedMatch
        return playerName == other.playerName &&
                botLevel == other.botLevel &&
                result == other.result &&
                playerPoints == other.playerPoints &&
                botPoints == other.botPoints
    }

    @Override
    override fun hashCode(): Int {
        var result = playerName.hashCode()
        result = 31 * result + botLevel.hashCode()
        result = 31 * result + this.result.hashCode()
        result = 31 * result + playerPoints
        result = 31 * result + botPoints
        return result
    }
}