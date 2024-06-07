package briscola.view

import briscola.model.Match
import briscola.model.Winner
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import java.net.URL
import java.util.*

/**
 * This class is the controller for the end game view.
 * @param stage the stage where the view is shown
 * @param match the match that has just ended
 */
class EndGameView(private val stage: Stage, private val match: Match) : Initializable {
    @FXML
    private lateinit var lblTitle: Label
    @FXML
    private lateinit var lblWin: Label
    @FXML
    private lateinit var lblPLayerPoints: Label
    @FXML
    private lateinit var lblBotPoints: Label
    @FXML
    private lateinit var listPLayerCards: ListView<String>
    @FXML
    private lateinit var listBotCards: ListView<String>

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        if (match.getWinner() == Winner.PLAYER) {
            lblTitle.text = "CONGRATULATIONS!"
            lblWin.text = "You won!"
        } else if (match.getWinner() == Winner.BOT) {
            lblTitle.text = "GAME OVER"
            lblWin.text = "You lost!"
        } else {
            lblTitle.text = "DRAW"
            lblWin.text = "It's a draw!"
        }

        lblPLayerPoints.text = "${match.player.name} points: ${match.player.points()}"
        lblBotPoints.text = "Bot points: ${match.bot.points()}"
        listPLayerCards.items.addAll(match.player.getGainedCards().map { it.toString() })
        listBotCards.items.addAll(match.bot.getGainedCards().map { it.toString() })
    }

    /**
     * Handles the click on the home button.
     */
    @FXML
    private fun goHome(event: MouseEvent) {
        SceneSwapper().swapScene(MenuView(stage), FxmlPath.MENU, stage)
    }
}