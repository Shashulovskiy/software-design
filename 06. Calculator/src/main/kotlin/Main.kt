import tokenizer.tokenize
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor

fun main() {
    println(PrintVisitor().visit(ParserVisitor().visit(tokenize(" ( 1 + 2  )  *  4  + 3   "))))
}
