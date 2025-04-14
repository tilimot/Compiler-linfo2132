package compiler.Parser;

import compiler.Exception.TypeErrorException;
import compiler.Parser.Grammar.Ast;
import compiler.Parser.Grammar.Expression;

import java.util.ArrayList;

public class Semantic {

    public Ast ast;

    public Semantic(Ast ast ){
        this.ast = ast;
    }

    public void startAnalysis () throws Exception {
        ast.semanticAnalysis();
    }

    public String checkType(String expression_value ) {
        if (expression_value.contains("\""))
            return "String";
        if (expression_value.contains("true") || expression_value.contains("false"))
            return "Boolean";
        if (expression_value.matches(".*[a-zA-Z].*"))
            return "Identifier";
        if (expression_value.matches(".*[0-9].*"))
            return "Integer";
        return expression_value;
    }

    public boolean checkExpressionsType(ArrayList<Expression> expressions) throws Exception {
        String lastExpressionType = expressions.getFirst().getType();
        for (Expression expression : expressions) {
            return true;
        }
        return true;
    }

}
