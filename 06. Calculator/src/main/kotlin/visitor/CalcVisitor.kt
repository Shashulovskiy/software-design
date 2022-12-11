package visitor

import tokenizer.*

class CalcVisitor : TokenVisitor {

    private val stack = mutableListOf<Int>()

    fun visit(tokens: List<Token>): Int {
        tokens.forEach { it.accept(this) }

        if (stack.size != 1) {
            throw IllegalArgumentException("Expression reduced to more than one number")
        }

        return stack.last()
    }

    override fun visit(token: NumberToken) {
        stack.add(token.number)
    }

    override fun visit(token: Brace) {
        throw IllegalArgumentException("CalcVisitor does not expect braces")
    }

    override fun visit(token: Operation) {
        if (stack.size < 2) {
            throw IllegalArgumentException("Invalid operand count for $token")
        }
        val second = stack.removeLast()
        val first = stack.removeLast()

        stack.add(
            when (token) {
                is Add -> first + second
                is Sub -> first - second
                is Mul -> first * second
                is Div -> first / second
            }
        )
    }
}