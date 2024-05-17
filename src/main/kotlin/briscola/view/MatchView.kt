package briscola.view

import briscola.model.Card
import briscola.model.Match
import briscola.model.Player
import briscola.utils.CardImage
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import java.net.URL
import java.util.*


/**
 * The view that represents the match.
 * @param stage the stage where the view is shown
 * @param playerName the name of the player
 * @constructor creates a new match view
 */
class MatchView(private val stage: Stage, private val playerName: String) : Initializable {
    @FXML
    private lateinit var btnQuit: Button
    @FXML
    private lateinit var lblBriscola: Label
    @FXML
    private lateinit var imgLastCard: ImageView
    @FXML
    private lateinit var imgDeck: ImageView
    @FXML
    private lateinit var imgPlayerHandCard1: ImageView
    @FXML
    private lateinit var imgPlayerHandCard2: ImageView
    @FXML
    private lateinit var imgPlayerHandCard3: ImageView
    @FXML
    private lateinit var imgBotHandCard1: ImageView
    @FXML
    private lateinit var imgBotHandCard2: ImageView
    @FXML
    private lateinit var imgBotHandCard3: ImageView

    private lateinit var match: Match

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        match = Match(Player(playerName), Card.entries.toMutableList())
        match.prepareMatch()
        lblBriscola.text = "Briscola: ${match.getBriscolaSuit().toString()}"
        btnQuit.setOnAction { quitMatch() }
        imgLastCard.image = match.getLastCard()?.let { CardImage.getImageById(it.getId()) }
        imgDeck.image = CardImage.BACK.image

        // set up hand card images of player
        imgPlayerHandCard1.image = CardImage.getImageById(match.player.getHandCards()[0].getId())
        imgPlayerHandCard2.image = CardImage.getImageById(match.player.getHandCards()[1].getId())
        imgPlayerHandCard3.image = CardImage.getImageById(match.player.getHandCards()[2].getId())

        // set up hand card images of bot
        imgBotHandCard1.image = CardImage.BACK.image
        imgBotHandCard2.image = CardImage.BACK.image
        imgBotHandCard3.image = CardImage.BACK.image
    }

    private fun quitMatch() {
        SceneSwapper().swapScene(MenuView(stage), FxmlPath.MENU, stage)
    }
}