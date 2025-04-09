package compiler.Parser.Grammar;

import java.util.ArrayList;

public class AssignementStatement extends Statement {
    String identifier;
    Type type;
    String equalOperator;
    ArrayList<Expression> expressions;
    String eol;


    public AssignementStatement(String identifier, Type type, String  equalOperator, ArrayList<Expression> expressions, String eol, int tabIndex) {
        super(tabIndex);
        this.identifier = identifier;
        this.type = type;
        this.equalOperator = equalOperator;
        this.expressions = expressions;
        this.eol = eol;


    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = t + "\t";
        type.tabIndex = tabIndex + 1;
        StringBuilder expressionsStr = new StringBuilder();
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex + 1;
            expressionsStr.append(expression.toString());
        }

        return t +"ASSIGN : " + "\n" +tNext + identifier +  "\n" + type.toString() +tNext+ equalOperator + "\n"+ expressionsStr +tNext + eol+ "\n";
    }
}

