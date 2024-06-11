package briscola.utils.jason

import briscola.model.Card
import briscola.model.Suit
import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

/**
 * Jason internal action used to get the lowest card among three cards.
 * Used by normal_bot_agent when it has to play first.
 * Plays the lowest card in the hand by value and rank.
 * If two cards have the same value, the one with the lowest rank is played.
 * @param card1 the first card id
 * @param card2 the second card id
 * @param card3 the third card id
 * @param briscolaSuit the briscola suit
 * @param lowestCard the id of the lowest card
 */
class get_lowest_card: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val cardsId = listOf(args[0].toString().toInt(), args[1].toString().toInt(), args[2].toString().toInt())
        var cards = listOf(Card.getCardById(cardsId[0]), Card.getCardById(cardsId[1]), Card.getCardById(cardsId[2]))
        println(cards)
        cards = cards.filter { it != Card.NULL}
        val briscolaSuit = Suit.fromString(args[3].toString())
        val sortedCards = cards.sortedBy { it.getValue() }
        var noBriscolaCards = sortedCards.filter { it.getSuit() != briscolaSuit }
        if (noBriscolaCards.isEmpty()) {
            noBriscolaCards = sortedCards
        }
        // if the lowest cards have the same value, return the one with lower rank
        return if (noBriscolaCards.size == 1) {
            un.unifies(args[4], ASSyntax.createNumber(noBriscolaCards[0].getId().toDouble()))
        } else {
            if (noBriscolaCards[0].getValue() == noBriscolaCards[1].getValue()) {
                un.unifies(args[4], ASSyntax.createNumber(noBriscolaCards.minBy { it.getRank() }.getId().toDouble()))
            } else {
                un.unifies(args[4], ASSyntax.createNumber(noBriscolaCards[0].getId().toDouble()))
            }
        }
    }
}