package briscola.view

import briscola.env.BriscolaEnvironment
import briscola.model.BotLevel
import briscola.model.Card
import briscola.model.Match
import briscola.model.Player
import briscola.utils.CardImage
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import jason.infra.centralised.RunCentralisedMAS
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import javafx.util.Duration
import java.io.File
import java.net.URL
import java.util.*


/**
 * The view that represents the match.
 * @param stage the stage where the view is shown
 * @param playerName the name of the player
 * @constructor creates a new match view
 */
class MatchView(
    private val stage: Stage,
    private val playerName: String,
    private val botLevel: BotLevel,
    private var briscolaEnvironment: BriscolaEnvironment
) : Initializable {
    @FXML
    private lateinit var btnQuit: Button
    @FXML
    private lateinit var lblBriscola: Label
    @FXML
    private lateinit var imgLastCard: ImageView
    @FXML
    private lateinit var imgDeck: ImageView
    @FXML
    private lateinit var imgPlayerHandCard0: ImageView
    @FXML
    private lateinit var imgPlayerHandCard1: ImageView
    @FXML
    private lateinit var imgPlayerHandCard2: ImageView
    @FXML
    private lateinit var imgBotHandCard0: ImageView
    @FXML
    private lateinit var imgBotHandCard1: ImageView
    @FXML
    private lateinit var imgBotHandCard2: ImageView
    @FXML
    private lateinit var imgBotPlayedCard: ImageView
    @FXML
    private lateinit var imgPlayerPlayedCard: ImageView
    @FXML
    private lateinit var lblDeckCardsLeft: Label
    @FXML
    private lateinit var lblBotGainedCards: Label
    @FXML
    private lateinit var lblPlayerGainedCards: Label

    lateinit var match: Match

    private val animationDuration = Duration.seconds(2.0)

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        match = Match(Player(playerName), Card.entries.toMutableList())
        match.prepareMatch()
        lblBriscola.text = "Briscola: ${match.getBriscolaSuit().toString()}"
        btnQuit.setOnAction { quitMatch() }

        updateImages()

        updateLabels()

        briscolaEnvironment.newMatch(this, botLevel)
    }

    private fun quitMatch() {
        SceneSwapper().swapScene(MenuView(stage, briscolaEnvironment), FxmlPath.MENU, stage)
        briscolaEnvironment.matchEnded()
    }

    private fun updateImages() {
        // set up last card image and deck image
        imgLastCard.image = match.getLastCard()?.let { CardImage.getImageById(it.getId()) }
        imgDeck.image = if (match.deck.isNotEmpty()) CardImage.BACK.image else null

        // set up hand card images of player
        val playerHandSize = match.player.getHandCards().size
        imgPlayerHandCard0.image = if (playerHandSize > 0) CardImage.getImageById(match.player.getHandCards()[0].getId()) else null
        imgPlayerHandCard1.image = if (playerHandSize > 1) CardImage.getImageById(match.player.getHandCards()[1].getId()) else null
        imgPlayerHandCard2.image = if (playerHandSize > 2) CardImage.getImageById(match.player.getHandCards()[2].getId()) else null

        // set up hand card images of bot
        val botHandSize = match.bot.getHandCards().size
        imgBotHandCard0.image = if (botHandSize > 0) CardImage.BACK.image else null
        imgBotHandCard1.image = if (botHandSize > 1) CardImage.BACK.image else null
        imgBotHandCard2.image = if (botHandSize > 2) CardImage.BACK.image else null

        // set up played card images
        highlightCardTurn(match.isPlayerTurn())
    }

    private fun highlightCardTurn(playerTurn: Boolean) {
        if (playerTurn) {
            imgBotPlayedCard.parent.id = "BorderedPane"
            imgPlayerPlayedCard.parent.id = "SelectedBorderedPane"
        } else {
            imgBotPlayedCard.parent.id = "SelectedBorderedPane"
            imgPlayerPlayedCard.parent.id = "BorderedPane"
        }
    }

    private fun updateLabels() {
        lblDeckCardsLeft.text = "Card(s) left: ${match.deck.size}"
        lblBotGainedCards.text = "Bot gained cards: ${match.bot.getGainedCards().size}"
        lblPlayerGainedCards.text = "Player gained cards: ${match.player.getGainedCards().size}"
    }

    private fun updateCardPlayed(player: Player, card: Card, cardImageView: ImageView? = null) {
        if (player == match.player) {
            imgPlayerPlayedCard.image = CardImage.getImageById(card.getId())
            cardImageView?.image = null
        } else {
            imgBotPlayedCard.image = CardImage.getImageById(card.getId())
            cardImageView?.image = null
        }
    }

    fun cardPlayed(player: Player, card: Card, cardImageView: ImageView? = null) {
        match.playCard(player, card)
        if (match.getPlayedCards().size % 2 == 1) {
            updateCardPlayed(player, card, cardImageView)
            highlightCardTurn(match.isPlayerTurn())
        } else {
            updateCardPlayed(player, card, cardImageView)

            // if second card is played, wait 2 seconds and update the view
            val timeline = Timeline(KeyFrame(animationDuration))
            timeline.cycleCount = 1
            timeline.setOnFinished {
                imgPlayerPlayedCard.image = null
                imgBotPlayedCard.image = null
                updateImages()
                updateLabels()
                if (match.getWinner() != null) {
                    SceneSwapper().swapScene(EndGameView(stage, match, briscolaEnvironment), FxmlPath.END_GAME, stage)
                    briscolaEnvironment.matchEnded()
                }
            }
            timeline.play()
        }
    }

    @FXML
    private fun onCardClicked(event: MouseEvent) {
        if (!match.isPlayerTurn()) return
        val pane = event.source as BorderPane
        val cardImageView = pane.children[0] as ImageView
        val card = when (cardImageView) {
            imgPlayerHandCard0 -> match.player.getHandCards()[0]
            imgPlayerHandCard1 -> match.player.getHandCards()[1]
            imgPlayerHandCard2 -> match.player.getHandCards()[2]
            else -> throw IllegalArgumentException("Invalid card image view")
        }
        if (match.player.getHandCards().contains(card)) {
            cardPlayed(match.player, card, cardImageView)
        }
    }

    @FXML
    private fun onMouseEntered(event: MouseEvent) {
        if (!match.isPlayerTurn()) return
        val source = event.source as BorderPane
        source.id = "SelectedBorderedPane"
    }

    @FXML
    private fun onMouseExited(event: MouseEvent) {
        val source = event.source as BorderPane
        source.id = "BorderedPane"
    }
}