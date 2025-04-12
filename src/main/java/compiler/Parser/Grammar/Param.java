package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Param {
    ArrayList<Expression> expressions;
    int tabIndex;


    public Param(ArrayList<Expression> expressions){
        this.expressions=expressions;
    }


    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder paramStr = new StringBuilder();
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex;
            paramStr.append(expression);
        }
        return paramStr.toString();
    }
}
