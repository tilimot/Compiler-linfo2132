package compiler.Parser.Grammar;

import java.util.ArrayList;

public class ReturnStatement extends Statement{
    String return_;
    ArrayList<Expression> expressions;
    String eol;


    public ReturnStatement(String return_, ArrayList<Expression> expressions, String eol, int tabIndex){
        super(tabIndex);
        this.return_ = return_;
        this.expressions = expressions;
        this.eol= eol;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String t = "\t".repeat(tabIndex);
        String tNext = t + "\t";
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex + 1;
            sb.append(expression);
        }
        return t +"RETURN : " + "\n" +tNext + return_ + "\n" + tNext + "EXPR : " + "\n" +  sb  + tNext + eol + "\n";
    }
}
