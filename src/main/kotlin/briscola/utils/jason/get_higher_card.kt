package briscola.utils.jason

import briscola.model.Card
import briscola.model.Suit
import briscola.utils.getHigherCard
import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

class get_higher_card: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Boolean {
        val card1 = extractCard(args[0].toString())
        val card2 = extractCard(args[1].toString())
        val briscolaSuit = Suit.fromString(args[2].toString())
        val result = getHigherCard(card1, card2, briscolaSuit) == card2
        return un.unifies(args[3], ASSyntax.createAtom(result.toString()))
    }

    private fun extractCard(cardString: String): Card {
        // a card string is like "card(id, suit, rank, value)"
        // remove "card("
        val cardStringEdited = cardString.substring(5, cardString.length - 1)
        val card = cardStringEdited.split(",")
        return Card.getCardFromId(card[0].toInt())
    }
}