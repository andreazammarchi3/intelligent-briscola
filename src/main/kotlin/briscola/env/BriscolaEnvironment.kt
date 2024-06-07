package briscola.env

import briscola.view.MatchView
import jason.asSyntax.Literal
import jason.asSyntax.Structure
import jason.environment.Environment
import java.util.stream.Collectors
import java.util.stream.Stream


class BriscolaEnvironment: Environment() {
    private var matchView: MatchView? = null

    private val playCard0 : Literal = Literal.parseLiteral("play_card(0)")
    private val playCard1 : Literal = Literal.parseLiteral("play_card(1)")
    private val playCard2 : Literal = Literal.parseLiteral("play_card(2)")

    init {
        stop()
    }

    fun newMatch(matchView: MatchView) {
        this.matchView = matchView
    }

    fun matchEnded() {
        matchView = null
    }

    override fun getPercepts(agName: String): Collection<Literal> {
        if (matchView == null) return emptyList()
        val percepts = Stream.concat(
            turnPercepts().stream(),
            handPercepts().stream()
        ).collect(Collectors.toList())
        println(percepts)
        return percepts
    }

    private fun turnPercepts(): Collection<Literal> {
        val botTurn = if (matchView != null) !matchView!!.match.isPlayerTurn() else false
        val literals = listOf(Literal.parseLiteral("turn($botTurn)"))
        return literals
    }

    private fun handPercepts(): Collection<Literal> {
        val handString = if (matchView != null) matchView!!.match.bot.getHandCards().joinToString(",") else null
        val literals = listOf(Literal.parseLiteral("hand($handString)"))
        return literals
    }

    override fun executeAction(agName: String?, action: Structure): Boolean {
        try {
            Thread.sleep(5000)
        } catch (ignored: InterruptedException) {
        }
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