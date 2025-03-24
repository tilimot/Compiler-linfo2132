package compiler.Parser.Grammar;

import java.util.ArrayList;

public class IfStatement extends Statement {
    String if_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    Block block;
    //TODO implement Blocks block;

    public IfStatement(String if_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis, Block block){
        this.if_ = if_;
        this.openingParenthesis = openingParenthesis;
        this.expressions = expressions;
        this.closingParenthesis = closingParenthesis;
        this.block = block;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Expression expression : expressions) {
            sb.append(expression.toString()).append("\t");
        }
        return if_ + "\n" + openingParenthesis + "\n" + sb.toString() + "\n" + closingParenthesis + "\n" + block.toString() + "\t";
    }
}
