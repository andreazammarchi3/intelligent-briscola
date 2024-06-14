package briscola.env

import briscola.model.BotLevel
import briscola.view.MatchView
import jason.asSyntax.Literal
import jason.asSyntax.Structure
import jason.environment.Environment
import jason.infra.centralised.RunCentralisedMAS
import java.lang.String.format


/**
 * Environment for the Briscola game.
 * It provides percepts to the agents and executes actions.
 */
class BriscolaEnvironment: Environment() {
    private var matchView: MatchView? = null
    private var mas: RunCentralisedMAS? = null
    private var botLevel: BotLevel? = null

    private val playCard0 : Literal = Literal.parseLiteral("play_card(0)")
    private val playCard1 : Literal = Literal.parseLiteral("play_card(1)")
    private val playCard2 : Literal = Literal.parseLiteral("play_card(2)")

    /**
     * Set the MAS instance.
     * It is used to stop the MAS when the match ends.
     * @param mas the MAS instance
     */
    fun setMAS(mas: RunCentralisedMAS) {
        this.mas = mas
    }

    /**
     * Start a new match.
     * @param matchView the view of the match
     * @param botLevel the level of the bot
     */
    fun newMatch(matchView: MatchView, botLevel: BotLevel) {
        this.matchView = matchView
        this.botLevel = botLevel
    }

    /**
     * End the match.
     */
    fun matchEnded() {
        matchView = null
    }

    override fun getPercepts(agName: String?): Collection<Literal> {
        if (matchView == null) return emptyList()
        val literals = listOf(
            botLevelLiteral(),
            turnLiteral(),
            handLiteral(),
            briscolaSuitLiteral(),
            playedCardLiteral()
        )
        return literals
    }

    override fun executeAction(agName: String?, action: Structure): Boolean {
        println("Executing action: $action \n")
        if (matchView == null) return false
        when (action) {
            playCard0 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.handCards[0], 0)
            playCard1 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.handCards[1], 1)
            playCard2 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.handCards[2], 2)
            else -> return false
        }
        return true
    }

    /**
     * Create a literal representing the bot level.
     * @return the [Literal]
     */
    private fun botLevelLiteral(): Literal {
        return Literal.parseLiteral(format("bot_level(%s)",
            botLevel.toString()
        ))
    }

    /**
     * Create a literal representing the turn.
     * @return the [Literal]
     */
    private fun turnLiteral(): Literal {
        return Literal.parseLiteral(format("turn(%s, %s)",
            !matchView!!.match.playerTurn,
            matchView!!.match.playedCards.size
        ))
    }

    /**
     * Create a literal representing the hand cards.
     * @return the [Literal]
     */
    private fun handLiteral(): Literal {
        val hand = matchView!!.match.bot.handCards
        val cardDetails = hand.joinToString(", ") { card ->
            "card(${card.id}, ${card.suit}, ${card.rank}, ${card.value})"
        }
        return Literal.parseLiteral("hand([$cardDetails])")
    }

    /**
     * Create a literal representing the played card.
     * @return the [Literal]
     */
    private fun playedCardLiteral(): Literal {
        val playedCard = matchView!!.match.playedCards.firstOrNull()
        return if (playedCard != null) {
            Literal.parseLiteral(format("played_card(card(%s, %s, %s, %s))",
                playedCard.id,
                playedCard.suit,
                playedCard.rank,
                playedCard.value
            ))
        } else {
            Literal.parseLiteral("played_card(none)")
        }
    }

    /**
     * Create a literal representing the briscola suit.
     * @return the [Literal]
     */
    private fun briscolaSuitLiteral(): Literal {
        return Literal.parseLiteral(format("briscola_suit(%s)", matchView!!.match.briscolaSuit.toString()))
    }
}