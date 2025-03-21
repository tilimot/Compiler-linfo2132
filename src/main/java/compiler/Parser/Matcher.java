package compiler.Parser;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import java.util.ArrayList;

public class Matcher {

    protected Parser parser;

    public Matcher(Parser parser) {
        this.parser = parser;

    }

    public Symbol match(TokenType token) throws Exception {
        if(this.parser.currentSymbol().getTokenType()!=token) {
            throw new Exception("No match found");
        } else {
            Symbol matchingSymbol = parser.currentSymbol();
            parser.advance();
            return matchingSymbol;
        }
    }


}
