package compiler.Parser.Grammar;

import java.util.ArrayList;

public class IfStatement extends Statement {
    String if_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    Block block;

    //TODO implement Blocks block;

    public IfStatement(String if_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis, Block block, int tabIndex){
        super(tabIndex);
        this.if_ = if_;
        this.openingParenthesis = openingParenthesis;
        this.expressions = expressions;
        this.closingParenthesis = closingParenthesis;
        this.block = block;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        block.tabIndex = tabIndex+1;
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex+1;
            sb.append(expression);
        }
        return t + "IF : " + "\n" + tNext + if_ + "\n" + tNext + openingParenthesis + "\n"
                + tNext+"COND :"+ "\n" + sb +tNext + closingParenthesis + "\n" + block;
    }
}
