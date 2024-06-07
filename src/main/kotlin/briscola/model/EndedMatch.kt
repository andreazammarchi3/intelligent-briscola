package briscola.model

class EndedMatch(val playerName: String, val result: String, val playerPoints: Int, val botPoints: Int) {
    /**
     * Convert the match to JSON format.
     */
    fun toJson(): String {
        return "{\"playerName\": \"$playerName\", \"result\": \"$result\", \"playerPoints\": $playerPoints, \"botPoints\": $botPoints}"
    }

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

    companion object {
        /**
         * Create a match from a JSON string.
         */
        fun fromJson(json: String): EndedMatch {
            val jsonMap = json.substring(1, json.length - 1).split(", ").associate {
                val keyValue = it.split(": ")
                Pair(keyValue[0], keyValue[1])
            }
            return EndedMatch(jsonMap["playerName"]!!, jsonMap["result"]!!, jsonMap["playerPoints"]!!.toInt(), jsonMap["botPoints"]!!.toInt())
        }
    }
}