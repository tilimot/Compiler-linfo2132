package compiler.Lexer;

import org.checkerframework.checker.interning.qual.EqualsMethod;

public class Symbol {
    private TokenType token;
    private String attribute;

    public Symbol(TokenType token, String attribute) {
        this.token = token;
        this.attribute = attribute;
    }

    public TokenType getTokenType() {
        return token;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return "<" + this.token + ", '" + this.attribute +"'>";
    }
}