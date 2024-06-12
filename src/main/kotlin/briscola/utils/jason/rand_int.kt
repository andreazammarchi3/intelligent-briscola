package briscola.utils.jason

import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.Term
import kotlin.random.Random

class rand_int: DefaultInternalAction() {
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Boolean {
        val min = args[0].toString().toInt()
        val max = args[1].toString().toInt()
        val randomInt = Random.nextInt(max - min + 1) + min
        return un.unifies(args[2], ASSyntax.createNumber(randomInt.toDouble()))
    }
}