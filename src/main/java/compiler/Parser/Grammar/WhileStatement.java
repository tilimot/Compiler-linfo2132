package compiler.Parser.Grammar;

import java.util.ArrayList;

public class WhileStatement extends Statement {
    String while_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    Block block;
    //TODO implement Blocks block;

    public WhileStatement(String while_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis, Block block){
        this.while_ = while_;
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
        return while_ + "\n" + openingParenthesis + "\n" + sb + "\n" + closingParenthesis + "\n" + block.toString() + "\t";
    }
}
