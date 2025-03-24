package compiler.Parser.Grammar;

import java.util.ArrayList;

public class WhileStatement extends Statement {
    String while_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    Block block;

    //TODO implement Blocks block;

    public WhileStatement(String while_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis, Block block, int tabIndex){
        super(tabIndex);
        this.while_ = while_;
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
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex+1;
            sb.append(expression.toString()).append("\t");
        }
        return t + "WHILE : " + "\n" + tNext + while_ + "\n" + tNext + openingParenthesis + "\n" + sb  + closingParenthesis + "\n" + block.toString();
    }
}
