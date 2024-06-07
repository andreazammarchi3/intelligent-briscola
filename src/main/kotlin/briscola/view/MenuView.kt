package briscola.view

import briscola.env.BriscolaEnvironment
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * This class is the controller for the menu view.
 * @param stage the stage to which the view is attached
 * @constructor creates a new menu view controller
 */
class MenuView(private val stage: Stage, private val briscolaEnvironment: BriscolaEnvironment) : Initializable {
    @FXML
    private lateinit var txtPlayerName: TextField

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {

    }

/**
     * Handles the click on the start button.
     */
    @FXML
    private fun startMatch() {
        val playerName = txtPlayerName.text
        if (playerName.isNotBlank() && playerName.isNotEmpty()) {
            SceneSwapper().swapScene(MatchView(stage, playerName, briscolaEnvironment), FxmlPath.MATCH, stage)
        } else {
            txtPlayerName.promptText = "Please enter a name"
        }
    }

    /**
     * Handles the click on the scoreboard button.
     */
    @FXML
    private fun goScoreboard() {
        SceneSwapper().swapScene(ScoreboardView(stage, briscolaEnvironment), FxmlPath.SCOREBOARD, stage)
    }
}