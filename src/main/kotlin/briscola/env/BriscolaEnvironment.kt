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
        return listOf<Literal>(
            Literal.parseLiteral(format("turn(%s)", if (matchView != null) !matchView!!.match.isPlayerTurn() else false)),
        )
    }

    private fun turnPercepts(): Collection<Literal> {
        val botTurn = if (matchView != null) !matchView!!.match.isPlayerTurn() else false
        val literals = listOf(Literal.parseLiteral("turn($botTurn)"))
        return literals
    }

    private fun handPercepts(): Collection<Literal> {
        var handString = ""
        if (matchView != null) {
            for (card in matchView!!.match.bot.getHandCards()) {
                handString += "card(${card.getRank()}, ${card.getSuit()}),"
            }
            handString = handString.dropLast(1)
        }
        val literals = listOf(Literal.parseLiteral("hand($handString)"))
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
        return true
    }
}