package compiler.Parser.Grammar;

import compiler.Lexer.TokenType;

public class ArrayDeclarationBracket extends Type {
    String leftBracket;
    String rightBracket;
    int tabIndex;

    public ArrayDeclarationBracket(String leftbracket, String rightBracket, int tabIndex){
        this.leftBracket = leftbracket;
        this.rightBracket = rightBracket;
        this.tabIndex = tabIndex;
    }

    public TokenType getType(){
        return TokenType.ARRAY_TYPE;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return "\n"+t+leftBracket + "\n"+t + rightBracket;
    }
}
