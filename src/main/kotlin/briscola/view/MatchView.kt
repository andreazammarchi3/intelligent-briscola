package briscola.view

import briscola.env.BriscolaEnvironment
import briscola.model.*
import briscola.utils.CardImage
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
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
import java.net.URL
import java.util.*


/**
 * The view that represents the match.
 * @param stage the stage where the view is shown
 * @param playerName the name of the player
 * @param botLevel the level of the bot
 * @param startingPlayer the starting player option
 * @param briscolaEnvironment the environment of the game
 */
class MatchView(
    private val stage: Stage,
    private val playerName: String,
    private val botLevel: BotLevel,
    private val startingPlayer: StartingPlayerOption,
    private var briscolaEnvironment: BriscolaEnvironment) : Initializable {
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
        match = Match(Player(playerName), Card.entries.toMutableList(), botLevel)
        match.prepareMatch(startingPlayerOption = startingPlayer)
        lblBriscola.text = "Briscola: ${match.briscolaSuit.toString()}"
        btnQuit.setOnAction { quitMatch() }

        updateImages()

        updateLabels()

        briscolaEnvironment.newMatch(this, botLevel)
    }

    /**
     * Plays the card of the player and the bot.
     * @param player the player that played the card
     * @param card the card that was played
     * @param cardHandPosition the position of the card in the hand
     */
    fun cardPlayed(player: Player, card: Card, cardHandPosition: Int) {
        match.playCard(player, card)
        if (match.playedCards.size % 2 == 1) {
            updateCardPlayed(player, card, cardHandPosition)
            highlightCardTurn(match.playerTurn)
        } else {
            updateCardPlayed(player, card, cardHandPosition)

            // if second card is played, wait 2 seconds and update the view
            val timeline = Timeline(KeyFrame(animationDuration))
            timeline.cycleCount = 1
            timeline.setOnFinished {
                imgPlayerPlayedCard.image = null
                imgBotPlayedCard.image = null
                updateImages()
                updateLabels()
                if (match.winner != null) {
                    SceneSwapper().swapScene(EndGameView(stage, match, briscolaEnvironment), FxmlPath.END_GAME, stage)
                    briscolaEnvironment.matchEnded()
                }
            }
            timeline.play()
        }
    }

    /**
     * Quits the match and goes back to the menu.
     */
    private fun quitMatch() {
        SceneSwapper().swapScene(MenuView(stage, briscolaEnvironment), FxmlPath.MENU, stage)
        briscolaEnvironment.matchEnded()
    }

    /**
     * Updates the images of the cards.
     */
    private fun updateImages() {
        // set up last card image and deck image
        imgLastCard.image = match.lastCard?.let { CardImage.getImageById(it.id) }
        imgDeck.image = if (match.deck.isNotEmpty()) CardImage.BACK.image else null

        // set up hand card images of player
        val playerHandSize = match.player.handCards.size
        imgPlayerHandCard0.image = if (playerHandSize > 0) CardImage.getImageById(match.player.handCards[0].id) else null
        imgPlayerHandCard1.image = if (playerHandSize > 1) CardImage.getImageById(match.player.handCards[1].id) else null
        imgPlayerHandCard2.image = if (playerHandSize > 2) CardImage.getImageById(match.player.handCards[2].id) else null

        // set up hand card images of bot
        val botHandSize = match.bot.handCards.size
        imgBotHandCard0.image = if (botHandSize > 0) CardImage.BACK.image else null
        imgBotHandCard1.image = if (botHandSize > 1) CardImage.BACK.image else null
        imgBotHandCard2.image = if (botHandSize > 2) CardImage.BACK.image else null

        // set up played card images
        highlightCardTurn(match.playerTurn)
    }

    /**
     * Highlights the card that is played by the player or the bot.
     */
    private fun highlightCardTurn(playerTurn: Boolean) {
        if (playerTurn) {
            imgBotPlayedCard.parent.id = "BorderedPane"
            imgPlayerPlayedCard.parent.id = "SelectedBorderedPane"
        } else {
            imgBotPlayedCard.parent.id = "SelectedBorderedPane"
            imgPlayerPlayedCard.parent.id = "BorderedPane"
        }
    }

    /**
     * Updates the labels of the view.
     */
    private fun updateLabels() {
        lblDeckCardsLeft.text = "Card(s) left: ${match.deck.size}"
        lblBotGainedCards.text = "Bot gained cards: ${match.bot.gainedCards.size}"
        lblPlayerGainedCards.text = "Player gained cards: ${match.player.gainedCards.size}"
    }

    /**
     * Updates the card played image by the player or the bot.
     * @param player the player that played the card
     * @param card the card that was played
     * @param cardHandPosition the position of the card in the hand
     */
    private fun updateCardPlayed(player: Player, card: Card, cardHandPosition: Int) {
        if (player == match.player) {
            imgPlayerPlayedCard.image = CardImage.getImageById(card.id)
            when (cardHandPosition) {
                0 -> imgPlayerHandCard0.image = null
                1 -> imgPlayerHandCard1.image = null
                2 -> imgPlayerHandCard2.image = null
            }
        } else {
            imgBotPlayedCard.image = CardImage.getImageById(card.id)
            when (cardHandPosition) {
                0 -> imgBotHandCard0.image = null
                1 -> imgBotHandCard1.image = null
                2 -> imgBotHandCard2.image = null
            }
        }
    }

    /**
     * Handles the event when a card is clicked.
     */
    @FXML
    private fun onCardClicked(event: MouseEvent) {
        if (!match.playerTurn) return
        val pane = event.source as BorderPane
        val cardImageView = pane.children[0] as ImageView
        val card = when (cardImageView) {
            imgPlayerHandCard0 -> match.player.handCards[0]
            imgPlayerHandCard1 -> match.player.handCards[1]
            imgPlayerHandCard2 -> match.player.handCards[2]
            else -> throw IllegalArgumentException("Invalid card image view")
        }
        if (match.player.handCards.contains(card)) {
            cardPlayed(match.player, card, match.player.handCards.indexOf(card))
        }
    }

    /**
     * Handles the event when the mouse enters a card.
     */
    @FXML
    private fun onMouseEntered(event: MouseEvent) {
        if (!match.playerTurn) return
        val source = event.source as BorderPane
        source.id = "SelectedBorderedPane"
    }

    /**
     * Handles the event when the mouse exits a card.
     */
    @FXML
    private fun onMouseExited(event: MouseEvent) {
        val source = event.source as BorderPane
        source.id = "BorderedPane"
    }
}