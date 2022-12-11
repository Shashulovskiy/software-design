package tokenizer

import visitor.TokenVisitor

sealed interface Token {
    fun accept(visitor: TokenVisitor)
}

class NumberToken(val number: Int): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString() = "NUMBER($number)"
}

sealed class Brace: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}
object LeftBrace: Brace() {
    override fun toString() = "LEFT"
}
object RightBrace: Brace() {
    override fun toString() = "RIGHT"
}


sealed interface Operation : Token {
    fun priority(): Int

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object Add: Operation {
    override fun priority() = 1
    override fun toString() = "PLUS"
}

object Sub: Operation {
    override fun priority() = 1
    override fun toString() = "MINUS"
}

object Mul: Operation {
    override fun priority() = 2
    override fun toString() = "MUL"
}

object Div: Operation {
    override fun priority() = 2
    override fun toString() = "DIV"
}