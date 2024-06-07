package briscola.utils

import briscola.model.EndedMatch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class IO {
    companion object {
        private const val FILE_PATH = "src/main/resources/scoreboard/endedMatches.json"
        private val gson = Gson()

        /**
         * Saves an EndedMatch object to a JSON file containing an array of EndedMatches.
         * @param endedMatch the match to save
         */
        fun saveEndedMatch(endedMatch: EndedMatch) {
            val matches = getEndedMatches().toMutableList()
            matches.add(endedMatch)
            val json = gson.toJson(matches)
            FileWriter(FILE_PATH).use { writer ->
                writer.write(json)
            }
        }

        /**
         * Reads all EndedMatches from the JSON file.
         * @return a list of EndedMatches
         */
        fun getEndedMatches(): List<EndedMatch> {
            val file = File(FILE_PATH)
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                FileWriter(file).use { it.write("[]") }  // Create an empty JSON array if the file doesn't exist
            }
            FileReader(file).use { reader ->
                val type = object : TypeToken<List<EndedMatch>>() {}.type
                return gson.fromJson(reader, type) ?: emptyList()
            }
        }
    }
}
