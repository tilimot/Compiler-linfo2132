package compiler.Lexer;

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
        return "Symbol {" + this.token + ", '" + this.attribute +"'}";
    }
}