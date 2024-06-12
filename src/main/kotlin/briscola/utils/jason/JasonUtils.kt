package briscola.utils.jason

import briscola.model.Card
import jason.asSyntax.Term

fun getCardsFromArgs(args: Array<Term>): List<Card> {
    var cardsId = listOf(args[0].toString().toInt(), args[1].toString().toInt(), args[2].toString().toInt())
    cardsId = cardsId.filter { it != -1 }
    return cardsId.map { Card.getCardFromId(it) }
}