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
}
