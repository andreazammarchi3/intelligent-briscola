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
        var cardsId = listOf(args[0].toString().toInt(), args[1].toString().toInt(), args[2].toString().toInt())
        cardsId = cardsId.filter { it != -1 }
        val cards = cardsId.map { Card.getCardById(it) }
        println("cards: $cards")
        val briscolaSuit = Suit.fromString(args[3].toString())
        println("briscola suit: $briscolaSuit")
        val opponentCard = Card.getCardById(args[4].toString().toInt())
        println("opponent card: $opponentCard")
        for (card in cards) {
            if (getHigherCard(card, opponentCard, briscolaSuit) == card) {
                println("winning card found: $card")
                return un.unifies(args[5], ASSyntax.createNumber(card.getId().toDouble()))
            }
        }
        val sortedCards = cards.sortedBy { it.getValue() }
        val noBriscolaCards = sortedCards.filter { it.getSuit() != briscolaSuit }
        val winningCard = if (noBriscolaCards.isNotEmpty()) {
            noBriscolaCards[0]
        } else {
            sortedCards[0]
        }
        println("winning card: $winningCard")
        return un.unifies(args[5], ASSyntax.createNumber(winningCard.getId().toDouble()))
    }
}