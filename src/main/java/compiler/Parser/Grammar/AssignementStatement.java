package compiler.Parser.Grammar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssignementStatement extends Statement {
    String identifier;
    Type type;
    String equalOperator;
    ArrayList<Expression> expressions;
    String eol;

    public AssignementStatement(String identifier, Type type, String  equalOperator, ArrayList<Expression> expressions, String eol){
        this.identifier = identifier;
        this.type = type;
        this.equalOperator = equalOperator;
        this.expressions = expressions;
        this.eol = eol;


    }

    @Override
    public String toString() {
        StringBuilder expressionsStr = new StringBuilder();
        for (Expression expression : expressions) {
            expressionsStr.append(expression.toString()).append(" ");
        }

        return identifier +  "\n" + type.toString() + "\t" + equalOperator + "\n" + "(" + "\n" + "\t" + expressionsStr.toString().trim() + "\n" + ")" + "\n" + eol + "\n";
    }
}

