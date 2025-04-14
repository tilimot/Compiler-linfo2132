package compiler.Parser.Grammar;

import compiler.Lexer.TokenType;

public class SimpleType extends Type {
    String value;
    int tabIndex;


    public SimpleType(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    public TokenType getType(){
        if (value.equals("int")){
            return TokenType.INTEGER;
        } else if (value.equals("float")){
            return TokenType.FLOAT;
        } else if (value.equals("string")){
            return TokenType.STRINGS;
        } else if (value.equals("bool")){
            return TokenType.BOOLEAN;
        } else {
            return TokenType.IDENTIFIER;
        }
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t +value;
    }
}
