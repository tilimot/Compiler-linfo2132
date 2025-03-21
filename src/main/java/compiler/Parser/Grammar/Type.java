package compiler.Parser.Grammar;

import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Matcher;
import compiler.Parser.Parser;


public class Type extends Matcher {

    private String identifier;

    public Type(Parser parser, String identifier) {
        super(parser);

    }

    public Type parseType() throws Exception {
        //TODO logic for parsing type
        Symbol identifier = match(TokenType.IDENTIFIER);
        return new Type(parser, (String) identifier.getAttribute());
    }
}

