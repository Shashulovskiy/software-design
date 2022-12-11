import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tokenizer.tokenize
import visitor.CalcVisitor
import visitor.ParserVisitor
import kotlin.test.assertEquals

class CalculatorTests {
    private fun testValid(expression: String, expectedResult: Int) =
        assertEquals(
            expectedResult,
            CalcVisitor().visit(ParserVisitor().visit(tokenize(expression)))
        )

    private fun testError(expression: String) =
        assertThrows<Exception> {
            CalcVisitor().visit(ParserVisitor().visit(tokenize(expression)))
        }

    @Test
    fun testSimple() {
        testValid("1", 1)
        testValid(" 1 ", 1)
        testValid(" 1+2 ", 3)
        testValid(" 1   +  2  ", 3)
        testValid(" ( 1   +  2  )  ", 3)
    }

    @Test
    fun testComplex() {
        testValid("(1 + 2) / 3 * 10", 10)
        testValid("(1 + 2) / (1 + 0) * 6 - 10 / (3 + 2) * 4", 10)
        testValid("(((15 + 4) * 10) - 58) + 7 * 1 * 10 - 14 * ((28 + 5) - 1)", -246)
    }

    @Test
    fun testError() {
        testError("(1")
        testError("1 +")
        testError("(1 + 2")
        testError("1 x 2")
    }
}