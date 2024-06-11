package briscola.utils.jason

import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term

class get_hand_card_position_by_id: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val hand = listOf(args[0].toString().toInt(), args[1].toString().toInt(), args[2].toString().toInt())
        val cardId = args[3].toString().toInt()
        return un.unifies(args[4], ASSyntax.createNumber(hand.indexOf(cardId).toDouble()))
    }
}