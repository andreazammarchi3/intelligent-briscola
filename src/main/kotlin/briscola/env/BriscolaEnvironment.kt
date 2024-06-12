package briscola.env

import briscola.model.BotLevel
import briscola.view.MatchView
import jason.asSyntax.Literal
import jason.asSyntax.Structure
import jason.environment.Environment
import jason.infra.centralised.RunCentralisedMAS
import java.lang.String.format


class BriscolaEnvironment: Environment() {
    private var matchView: MatchView? = null
    private var mas: RunCentralisedMAS? = null
    private var botLevel: BotLevel? = null

    private val playCard0 : Literal = Literal.parseLiteral("play_card(0)")
    private val playCard1 : Literal = Literal.parseLiteral("play_card(1)")
    private val playCard2 : Literal = Literal.parseLiteral("play_card(2)")

    fun setMAS(mas: RunCentralisedMAS) {
        this.mas = mas
    }

    fun newMatch(matchView: MatchView, botLevel: BotLevel) {
        this.matchView = matchView
        this.botLevel = botLevel
    }

    fun matchEnded() {
        matchView = null
    }

    override fun getPercepts(agName: String?): Collection<Literal> {
        if (matchView == null) return emptyList()
        val literals = listOf(
            matchStartedLiteral(),
            turnLiteral(),
            handLiteral(),
            briscolaSuitLiteral(),
            playedCardLiteral()
        )
        // println("Percepts: $literals")
        return literals
    }

    private fun matchStartedLiteral(): Literal {
        return Literal.parseLiteral(format("match_started(%s)",
            botLevel.toString()
        ))
    }

    private fun turnLiteral(): Literal {
        return Literal.parseLiteral(format("turn(%s, %s)",
            !matchView!!.match.isPlayerTurn(),
            matchView!!.match.getPlayedCards().size
        ))
    }

    private fun handLiteral(): Literal {
        val hand = matchView!!.match.bot.getHandCards()
        return Literal.parseLiteral(format("hand(%s, %s, %s)",
            if (hand.isNotEmpty()) (hand[0].getId()) else -1,
            if (hand.size > 1) hand[1].getId() else -1,
            if (hand.size > 2) hand[2].getId() else -1
        ))
    }

    private fun playedCardLiteral(): Literal {
        val playedCards = matchView!!.match.getPlayedCards()
        return Literal.parseLiteral(format("played_card(%s)",
            if (playedCards.isNotEmpty()) playedCards[0].getId() else -1
        ))
    }

    private fun briscolaSuitLiteral(): Literal {
        return Literal.parseLiteral(format("briscola_suit(%s)", matchView!!.match.getBriscolaSuit().toString()))
    }

    override fun executeAction(agName: String?, action: Structure): Boolean {
        println("Executing action: $action")
        if (matchView == null) return false
        when (action) {
            playCard0 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.getHandCards()[0])
            playCard1 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.getHandCards()[1])
            playCard2 -> matchView!!.cardPlayed(matchView!!.match.bot, matchView!!.match.bot.getHandCards()[2])
            else -> return false
        }
        return true
    }
}