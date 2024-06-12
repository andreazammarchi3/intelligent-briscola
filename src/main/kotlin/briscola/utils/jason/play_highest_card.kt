package briscola.utils.jason

import briscola.model.Card
import briscola.model.Suit
import briscola.utils.getSameSuitCards
import briscola.utils.orderCardsByHigherValueAndHigherRank
import briscola.utils.orderCardsByLowerValueAndLowerRank
import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

/**
 * Jason internal action used by normal_bot_agent when it has to play first.
 * Try to play the briscola if possible, unless there are no briscola cards in hand.
 * Plays the highest card in the hand by value and rank.
 * @param Card1 the first card id
 * @param Card2 the second card id
 * @param Card3 the third card id
 * @param BriscolaSuit the briscola suit
 * @param PlayedCard the id of the played card
 * @param HighestCard the id of the lowest card
 */
class play_highest_card: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val cards = getCardsFromArgs(args)
        println("Cards: $cards")
        val briscolaSuit = Suit.fromString(args[3].toString())
        val playedCard = Card.getCardFromId(args[4].toString().toInt())
        println("Played card: $playedCard")

        // Step 1: Try to play with briscola if possible, unless there are no briscola cards in hand
        val briscolaCards = getSameSuitCards(cards, briscolaSuit)
        var cardsToPlay = briscolaCards.ifEmpty {
            // If there are no briscola cards in hand
            // Step 2: Check if there are cards of the same suit as the played card
            getSameSuitCards(cards, playedCard.getSuit())
        }

        if (cardsToPlay.isEmpty()) {
            // If there are no cards of the same suit as the played card and no briscola cards in hand
            // Step 3A: Play the lowest card by value and rank
            val handCards = cards
            cardsToPlay = orderCardsByLowerValueAndLowerRank(handCards)
            val lowestCard = cardsToPlay.first()
            println("Playing lowest card because there are no cards of the same suit as the played card and no briscola cards in hand: $lowestCard")
            return un.unifies(args[5], ASSyntax.createNumber(cards.indexOf(lowestCard).toDouble()))
        } else {
            // Else if there are cards of the same suit as the played card or briscola cards in hand
            // Step 3B: Play the highest card by value and rank
            cardsToPlay = orderCardsByHigherValueAndHigherRank(cardsToPlay)
            val highestCard = cardsToPlay.first()
            println("Highest card: $highestCard")
            return un.unifies(args[5], ASSyntax.createNumber(cards.indexOf(highestCard).toDouble()))
        }
    }
}