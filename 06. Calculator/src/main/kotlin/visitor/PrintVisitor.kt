package visitor

import tokenizer.Brace
import tokenizer.NumberToken
import tokenizer.Operation
import tokenizer.Token

class PrintVisitor: TokenVisitor {
    private val tokenStrings = mutableListOf<String>()

    fun visit(tokens: List<Token>): String {
        tokens.forEach {
            it.accept(this)
        }

        return tokenStrings.joinToString(" ")
    }

    override fun visit(token: NumberToken) {
        tokenStrings.add(token.toString())
    }

    override fun visit(token: Brace) {
        tokenStrings.add(token.toString())
    }

    override fun visit(token: Operation) {
        tokenStrings.add(token.toString())
    }
}