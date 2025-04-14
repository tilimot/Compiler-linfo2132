package compiler.Parser.Grammar;

import compiler.Lexer.TokenType;
import compiler.Parser.Semantic;

public class Expression {
    String value;
    int tabIndex;



    public Expression(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    public String getValue() {
        return value;
    }

    public TokenType getType(){
        return Semantic.checkType(value);
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        return tNext+value + "\n";
    }
}
