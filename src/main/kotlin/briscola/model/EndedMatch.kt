package briscola.model

class EndedMatch(val playerName: String, val result: String, val playerPoints: Int, val botPoints: Int) {
    /**
     * Convert the match to JSON format.
     */
    fun toJson(): String {
        return "{\"playerName\": \"$playerName\", \"result\": \"$result\", \"playerPoints\": $playerPoints, \"botPoints\": $botPoints}"
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