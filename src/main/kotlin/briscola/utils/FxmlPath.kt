package briscola.utils

/**
 * Enum class that contains the paths of the FXML files.
 * @param path the path of the FXML file.
 */
enum class FxmlPath(val path: String) {
    MENU("Menu.fxml"),
    MATCH("Match.fxml"),
    END_GAME("EndGame.fxml")
}