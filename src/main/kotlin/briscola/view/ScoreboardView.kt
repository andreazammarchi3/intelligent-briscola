package briscola.view

import briscola.env.BriscolaEnvironment
import briscola.model.EndedMatch
import briscola.utils.FxmlPath
import briscola.utils.IO
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * This class is the controller for the scoreboard view.
 * @param stage the stage where the view is shown
 */
class ScoreboardView(private val stage: Stage, private val briscolaEnvironment: BriscolaEnvironment) : Initializable {
    @FXML
    private lateinit var tableScoreboard: TableView<EndedMatch>
    @FXML
    private lateinit var colPlayerName: TableColumn<EndedMatch, String>
    @FXML
    private lateinit var colResult: TableColumn<EndedMatch, String>
    @FXML
    private lateinit var colPlayerPoints: TableColumn<EndedMatch, String>
    @FXML
    private lateinit var colBotPoints: TableColumn<EndedMatch, String>

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        val endedMatches = IO.getEndedMatches()
        colPlayerName.cellValueFactory = PropertyValueFactory("playerName")
        colResult.cellValueFactory = PropertyValueFactory("result")
        colPlayerPoints.cellValueFactory = PropertyValueFactory("playerPoints")
        colBotPoints.cellValueFactory = PropertyValueFactory("botPoints")
        tableScoreboard.items.addAll(endedMatches)
    }

    /**
     * Handles the click on the home button.
     */
    @FXML
    private fun goHome() {
        SceneSwapper().swapScene(MenuView(stage, briscolaEnvironment), FxmlPath.MENU, stage)
    }
}
