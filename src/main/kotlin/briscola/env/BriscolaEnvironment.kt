package briscola.env

import briscola.view.MatchView
import jason.asSyntax.Literal
import jason.asSyntax.Structure
import jason.environment.Environment
import java.lang.String.format


class BriscolaEnvironment: Environment() {
    private var matchView: MatchView? = null

    private val playCard0 : Literal = Literal.parseLiteral("play_card(0)")
    private val playCard1 : Literal = Literal.parseLiteral("play_card(1)")
    private val playCard2 : Literal = Literal.parseLiteral("play_card(2)")

    fun newMatch(matchView: MatchView) {
        this.matchView = matchView
    }

    fun matchEnded() {
        matchView = null
    }

    override fun getPercepts(agName: String?): Collection<Literal> {
        if (matchView == null) return emptyList()
        val literals = listOf<Literal>(
            Literal.parseLiteral(format("turn(%s, %s)",
                !matchView!!.match.isPlayerTurn(),
                matchView!!.match.getPlayedCards().size))
        )
        // println("Percepts: $literals")
        return literals
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
        try {
            Thread.sleep(4000) // Slowdown the system
        } catch (ignored: InterruptedException) {
        }
        return true
    }
}