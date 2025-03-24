package compiler.Parser.Grammar;

import java.util.ArrayList;

public class IfStatement extends Statement {
    String if_;
    String openingParenthesis;
    ArrayList<Expression> expressions;
    String closingParenthesis;
    //TODO implement Blocks block;

    public IfStatement(String if_, String openingParenthesis, ArrayList<Expression> expressions, String closingParenthesis){
        this.if_ = if_;
        this.openingParenthesis = openingParenthesis;
        this.expressions = expressions;
        this.closingParenthesis = closingParenthesis;
    }

}
