package briscola.utils

import kotlin.test.*

/**
 * Test class for [FxmlPath].
 */
class TestFxmlPath {
    /**
     * Test the [FxmlPath] values.
     */
    @Test
    fun testFxmlPath() {
        val menu = FxmlPath.MENU
        assertEquals("Menu.fxml", menu.path)

        val match = FxmlPath.MATCH
        assertEquals("Match.fxml", match.path)

        val scoreboard = FxmlPath.SCOREBOARD
        assertEquals("Scoreboard.fxml", scoreboard.path)

        val endGame = FxmlPath.END_GAME
        assertEquals("EndGame.fxml", endGame.path)
    }
}