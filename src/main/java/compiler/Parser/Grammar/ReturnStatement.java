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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Expression expression : expressions) {
            sb.append(expression.toString()).append("\t");
        }
        return return_ + "\n" + sb + "\n" + eol + "\n";
    }
}
