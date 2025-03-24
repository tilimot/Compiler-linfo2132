package compiler.Parser.Grammar;

import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Matcher;
import compiler.Parser.Parser;

import java.util.ArrayList;

public class Assignement extends Matcher {

    public Assignement(Parser parser) {
        super(parser);
    }

    public Assignement parseAssignement() throws Exception {
        return new Assignement(parser);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
