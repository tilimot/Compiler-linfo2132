package compiler.Parser.Grammar;

import java.util.ArrayList;

public class WhileStatement extends Statement {
    String while_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    //TODO implement Blocks block;

    public WhileStatement(String while_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis){
        this.while_ = while_;
        this.openingParenthesis = openingParenthesis;
        this.expressions = expressions;
        this.closingParenthesis = closingParenthesis;
    }

}
