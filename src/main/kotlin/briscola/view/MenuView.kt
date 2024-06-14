package briscola.view

import briscola.env.BriscolaEnvironment
import briscola.model.BotLevel
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * This class is the controller for the menu view.
 * @param stage the stage to which the view is attached
 * @param briscolaEnvironment the environment of the game
 * @constructor creates a new menu view controller
 */
class MenuView(private val stage: Stage, private val briscolaEnvironment: BriscolaEnvironment) : Initializable {
    @FXML
    private lateinit var txtPlayerName: TextField
    @FXML
    private lateinit var comboBotLevel: ComboBox<String>

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        comboBotLevel.items.addAll(BotLevel.entries.map { it.toString() })
        comboBotLevel.selectionModel.selectFirst()
    }

    /**
     * Handles the click on the start button.
     */
    @FXML
    private fun startMatch() {
        val playerName = txtPlayerName.text
        if (playerName.isNotBlank() && playerName.isNotEmpty()) {
            val botLevel = BotLevel.fromString(comboBotLevel.selectionModel.selectedItem)
            SceneSwapper().swapScene(MatchView(stage, playerName, botLevel, briscolaEnvironment), FxmlPath.MATCH, stage)
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