package visitor

import tokenizer.Brace
import tokenizer.LeftBrace
import tokenizer.NumberToken
import tokenizer.Operation
import tokenizer.RightBrace
import tokenizer.Token

class ParserVisitor: TokenVisitor {
    private val resultTokens = mutableListOf<Token>()
    private val stack = mutableListOf<Token>()

    fun visit(tokens: List<Token>): List<Token> {
        tokens.forEach {
            it.accept(this)
        }

        while (stack.isNotEmpty()) {
            resultTokens.add(stack.removeLast())
        }

        return resultTokens
    }

    override fun visit(token: NumberToken) {
        resultTokens.add(token)
    }

    override fun visit(token: Operation) {
        while (stack.isNotEmpty()) {
            val last = stack.last()
            if (last is Operation && last.priority() >= token.priority()) {
                resultTokens.add(stack.removeLast())
            } else {
                break
            }
        }
        stack.add(token)
    }

    override fun visit(token: Brace) {
        when (token) {
            is LeftBrace -> stack.add(token)
            is RightBrace -> {
                while (stack.last() != LeftBrace) {
                    resultTokens.add(stack.removeLast())
                }
                stack.removeLast()
            }
        }
    }
}