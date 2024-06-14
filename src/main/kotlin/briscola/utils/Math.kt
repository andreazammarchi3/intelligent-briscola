package briscola.utils

import briscola.model.Card
import briscola.model.Suit

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
    if (card1.suit == briscolaSuit && card2.suit != briscolaSuit) {
        return card1
    } else if (card1.suit != briscolaSuit && card2.suit == briscolaSuit) {
        return card2
    } else {
        return if (card1.suit != card2.suit) {
            card1
        } else {
            if (card1.value < card2.value) {
                card2
            } else if (card1.value > card2.value) {
                card1
            } else {
                if (card1.rank < card2.rank) {
                    card2
                } else {
                    card1
                }
            }
        }
    }
}
