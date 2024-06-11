package briscola.utils.jason

import briscola.model.Card
import briscola.model.Suit
import briscola.utils.Math.Companion.getHigherCard
import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

/**
 * Jason internal action used to get the lowest card among three cards.
 * Used by normal_bot_agent when it has to play second.
 * Finds a winning card among the three cards.
 * If no card can win, returns the lowest card by value.
 * @param card1 the first card id
 * @param card2 the second card id
 * @param card3 the third card id
 * @param briscolaSuit the briscola suit
 * @param opponentCard the opponent card
 * @param winningCard the winning card id
 */
class get_winning_card: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val cardsId = listOf(args[0].toString().toInt(), args[1].toString().toInt(), args[2].toString().toInt())
        var cards = listOf(Card.getCardById(cardsId[0]), Card.getCardById(cardsId[1]), Card.getCardById(cardsId[2]))
        println(cards)
        cards = cards.filter { it != Card.NULL}
        val briscolaSuit = Suit.fromString(args[3].toString())
        val opponentCard = Card.getCardById(args[4].toString().toInt())
        for (card in cards) {
            if (getHigherCard(card, opponentCard, briscolaSuit) == card) {
                return un.unifies(args[5], ASSyntax.createNumber(card.getId().toDouble()))
            }
        }
        val sortedCards = cards.sortedBy { it.getValue() }
        val noBriscolaCards = sortedCards.filter { it.getSuit() != briscolaSuit }
        return if (noBriscolaCards.isNotEmpty()) {
            un.unifies(args[5], ASSyntax.createNumber(noBriscolaCards[0].getId().toDouble()))
        } else {
            un.unifies(args[5], ASSyntax.createNumber(sortedCards[0].getId().toDouble()))
        }
    }
}