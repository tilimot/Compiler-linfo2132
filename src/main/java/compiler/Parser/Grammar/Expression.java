package compiler.Parser.Grammar;

import compiler.Lexer.TokenType;
import compiler.Semantic.Semantic;

import java.util.ArrayList;

public class Expression {
    String value;
    int tabIndex;
    String attribute;
    ArrayList<Param> params;



    public Expression(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }
    public Expression(String value, int tabIndex, String attribute) {
        this.value = value;
        this.tabIndex = tabIndex;
        this.attribute = attribute;
    }

    public Expression(String value, int tabIndex, ArrayList<Param> params){
        this.value = value;
        this.tabIndex = tabIndex;
        this.params = params;
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
        if (attribute != null) {
            return tNext+value+ " -> " + attribute +  "\n";
        }
        if(params != null){
            return tNext+value+"\n"+tNext+ "PARAMS : "+"\n" +tNext + params + "\n";
        }
        return tNext+value + "\n";
    }
}
