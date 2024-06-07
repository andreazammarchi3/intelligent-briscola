package briscola.model

class EndedMatch(val playerName: String, val result: String, val playerPoints: Int, val botPoints: Int) {

    @Override
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as EndedMatch
        return playerName == other.playerName && result == other.result && playerPoints == other.playerPoints && botPoints == other.botPoints
    }

    @Override
    override fun hashCode(): Int {
        var result = playerName.hashCode()
        result = 31 * result + result.hashCode()
        result = 31 * result + playerPoints
        result = 31 * result + botPoints
        return result
    }
}