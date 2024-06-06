package briscola.view

import briscola.model.Card
import briscola.model.Match
import briscola.model.Player
import briscola.model.Winner
import briscola.utils.CardImage
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
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

    private lateinit var match: Match

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        match = Match(Player(playerName), Card.entries.toMutableList())
        match.prepareMatch()
        lblBriscola.text = "Briscola: ${match.getBriscolaSuit().toString()}"
        btnQuit.setOnAction { quitMatch() }

        updateImages()

        updateLabels()

        if (!match.isPlayerTurn()) {
            botTurn()
        }
    }

    private fun quitMatch() {
        SceneSwapper().swapScene(MenuView(stage), FxmlPath.MENU, stage)
    }

    private fun updateImages() {
        // set up last card image and deck image
        imgLastCard.image = match.getLastCard()?.let { CardImage.getImageById(it.getId()) }
        imgDeck.image = CardImage.BACK.image

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

    private fun cardPlayed(player: Player, card: Card) {
        match.playCard(player, card)

        if (player == match.player) {
            imgPlayerPlayedCard.image = CardImage.getImageById(card.getId())
        } else {
            imgBotPlayedCard.image = CardImage.getImageById(card.getId())
        }

        updateImages()
        updateLabels()

        if (match.getWinner() != null) {
            SceneSwapper().swapScene(MenuView(stage), FxmlPath.MENU, stage)
        } else {
            if (!match.isPlayerTurn()) {
                botTurn()
            }
        }
    }

    private fun botTurn() {
        cardPlayed(match.bot, match.bot.playCard())
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
            cardPlayed(match.player, card)
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