package briscola.utils

import briscola.model.Card
import briscola.model.Suit

/**
 * Utility class for math operations.
 */
class Math {
    companion object {
        /**
         * Returns the higher card between two cards.
         * If a card is the briscola, it is considered higher than any other card.
         * If the two cards have different suits, the card with the first suit is returned.
         * If the two cards have the same suit, the card with the highest value is returned.
         * If the two cards have the same value, the card with the highest rank is returned.
         * @param card1 the first card
         * @param card2 the second card
         * @param briscolaSuit the briscola suit
         * @return the higher card
         */
        fun getHigherCard(card1: Card, card2: Card, briscolaSuit: Suit): Card {
            if (card1.getSuit() == briscolaSuit && card2.getSuit() != briscolaSuit) {
                return card1
            } else if (card1.getSuit() != briscolaSuit && card2.getSuit() == briscolaSuit) {
                return card2
            } else {
                return if (card1.getSuit() != card2.getSuit()) {
                    card1
                } else {
                    if (card1.getValue() < card2.getValue()) {
                        card2
                    } else if (card1.getValue() > card2.getValue()) {
                        card1
                    } else {
                        if (card1.getRank() < card2.getRank()) {
                            card2
                        } else {
                            card1
                        }
                    }
                }
            }
        }
    }
}