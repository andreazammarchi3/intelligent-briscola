package briscola

import briscola.view.MatchView
import jason.asSyntax.Literal
import jason.asSyntax.Structure
import jason.environment.Environment
import java.util.stream.Collectors
import java.util.stream.Stream


class Env(private var matchView: MatchView): Environment() {
    private val playCard0 : Literal = Literal.parseLiteral("play_card(0)")
    private val playCard1 : Literal = Literal.parseLiteral("play_card(1)")
    private val playCard2 : Literal = Literal.parseLiteral("play_card(2)")

    override fun getPercepts(agName: String): Collection<Literal> {
        return Stream.concat(
            turnPercepts(agName).stream(),
            handPercepts(agName).stream()
        ).collect(Collectors.toList())
    }

    private fun turnPercepts(agent: String): Collection<Literal> {
        val botTurn = !matchView.match.isPlayerTurn()
        return listOf(Literal.parseLiteral("turn($agent,$botTurn)"))
    }

    private fun handPercepts(agent: String): Collection<Literal> {
        val handString = matchView.match.bot.getHandCards().joinToString(",")
        return listOf(Literal.parseLiteral("hand($agent,$handString)"))
    }

    override fun executeAction(agName: String?, action: Structure): Boolean {
        when (action) {
            playCard0 -> matchView.cardPlayed(matchView.match.bot, matchView.match.bot.getHandCards()[0])
            playCard1 -> matchView.cardPlayed(matchView.match.bot, matchView.match.bot.getHandCards()[1])
            playCard2 -> matchView.cardPlayed(matchView.match.bot, matchView.match.bot.getHandCards()[2])
            else -> return false
        }
        return true
    }
}