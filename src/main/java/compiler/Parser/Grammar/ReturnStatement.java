package compiler.Parser.Grammar;

import java.util.ArrayList;

public class ReturnStatement extends Statement{
    String return_;
    ArrayList<Expression> expressions;
    String eol;

    public ReturnStatement(String return_, ArrayList<Expression> expressions, String eol){
        this.return_ = return_;
        this.expressions = expressions;
        this.eol = eol;
    }
}
