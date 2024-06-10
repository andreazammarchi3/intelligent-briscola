package briscola.utils.jason

import jason.asSemantics.DefaultInternalAction
import jason.asSemantics.TransitionSystem
import jason.asSemantics.Unifier
import jason.asSyntax.ASSyntax
import jason.asSyntax.ListTerm
import jason.asSyntax.ListTermImpl
import jason.asSyntax.Term

class split : DefaultInternalAction() {
    @Throws(Exception::class)
    override fun execute(ts: TransitionSystem, un: Unifier, args: Array<Term>): Any {
        val stringToSplit = args[0].toString()
        val delimiter = args[1].toString()
        val result: ListTerm = ListTermImpl()

        for (item in stringToSplit.split(delimiter.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            result.add(ASSyntax.createString(item.trim { it <= ' ' }))
        }

        return un.unifies(args[2], result)
    }
}