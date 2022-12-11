package tokenizer

fun tokenize(input: String): List<Token> {
    val stateContext = StateContext()

    input.forEach {
        stateContext.consumeChar(it)
    }
    stateContext.consumeEOF()

    return stateContext.getTokens()
}

sealed interface TokenizerState {
    fun handleNextChar(char: Char, ctx: StateContext)
    fun handleEOF(ctx: StateContext)
}

data class StateContext(
    private var currentState: TokenizerState = StartState,
    private val tokens: MutableList<Token> = mutableListOf()
) {
    fun consumeChar(char: Char) {
        currentState.handleNextChar(char, this)
    }

    fun consumeEOF() {
        currentState.handleEOF(this)
    }

    fun getTokens(): List<Token> {
        return tokens
    }

    fun updateState(nextState: TokenizerState, token: Token?) {
        token?.let { tokens.add(it) }
        currentState = nextState
    }
}

object StartState : TokenizerState {
    override fun handleNextChar(char: Char, ctx: StateContext) {
        if (char.isWhitespace()) {
            ctx.updateState(this, null)
        } else {
            when (char) {
                '+' -> ctx.updateState(this, Add)
                '-' -> ctx.updateState(this, Sub)
                '*' -> ctx.updateState(this, Mul)
                '/' -> ctx.updateState(this, Div)
                '(' -> ctx.updateState(this, LeftBrace)
                ')' -> ctx.updateState(this, RightBrace)
                in '0'..'9' -> ctx.updateState(NumberState(char - '0'), null)
                else -> throw IllegalStateException("Unexpected char $char")
            }
        }
    }

    override fun handleEOF(ctx: StateContext) {
        ctx.updateState(EndState, null)
    }
}

class NumberState(var number: Int) : TokenizerState {
    override fun handleNextChar(char: Char, ctx: StateContext) {
        when (char) {
            in '0'..'9' -> {
                number = number * 10 + (char - '0')
            }
            else -> {
                ctx.updateState(StartState, NumberToken(number))
                StartState.handleNextChar(char, ctx)
            }
        }
    }

    override fun handleEOF(ctx: StateContext) {
        ctx.updateState(EndState, NumberToken(number))
    }
}

object EndState: TokenizerState {
    override fun handleNextChar(char: Char, ctx: StateContext) {
        throw IllegalStateException("Unexpected char after EOF")
    }

    override fun handleEOF(ctx: StateContext) {
        throw IllegalStateException("Unexpected char after EOF")
    }

}

