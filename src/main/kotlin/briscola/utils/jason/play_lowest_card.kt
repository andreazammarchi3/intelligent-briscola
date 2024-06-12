package briscola.utils.jason

import briscola.model.Suit
import briscola.utils.getDifferentSuitCards
import briscola.utils.orderCardsByLowerValueAndLowerRank
import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

/**
 * Jason internal action used by normal_bot_agent when it has to play first.
 * Try not to play the briscola if possible, unless it is the only option.
 * Plays the lowest card in the hand by value and rank.
 * @param Card1 the first card id
 * @param Card2 the second card id
 * @param Card3 the third card id
 * @param BriscolaSuit the briscola suit
 * @param LowestCard the id of the lowest card
 */
class play_lowest_card: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val cards = getCardsFromArgs(args)
        println("Cards: $cards")
        val briscolaSuit = Suit.fromString(args[3].toString())

        // Step 1: Try not to play the briscola if possible, unless it is the only option.
        val nonBriscolaCards = getDifferentSuitCards(cards, briscolaSuit)
        var cardsToPlay = nonBriscolaCards.ifEmpty {
            // If there are non briscola cards in hand
            cards
        }

        // Step 2: Order the cards by value ascending, then by rank ascending
        cardsToPlay = orderCardsByLowerValueAndLowerRank(cardsToPlay)

        // Step 3: Play the lowest card
        val lowestCard = cardsToPlay.first()
        println("Lowest card: $lowestCard")
        return un.unifies(args[4], ASSyntax.createNumber(cards.indexOf(lowestCard).toDouble()))
    }
}