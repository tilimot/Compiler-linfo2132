package compiler.Parser;

import compiler.Lexer.TokenType;
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

    public static TokenType checkType(String expression_value) {
        if (expression_value.contains("\""))
            return TokenType.STRINGS;
        if (expression_value.contains("true") || expression_value.contains("false"))
            return TokenType.BOOLEAN;
        if (expression_value.contains("."))
            return TokenType.FLOAT;
        if (expression_value.matches(".*[a-zA-Z].*"))
            return TokenType.IDENTIFIER;
        if (expression_value.matches(".*[0-9].*"))
            return TokenType.INTEGER;
        return TokenType.OPERATOR;

    }

    public static void checkExpressionsType(ArrayList<Expression> expressions) throws Exception {
        if (expressions.isEmpty()) return;

        TokenType baseType = expressions.getFirst().getType();
        expressions.removeFirst();
        for (Expression expression : expressions) {
            TokenType currentType = expression.getType();
            if (currentType != baseType && currentType != TokenType.IDENTIFIER && baseType != TokenType.IDENTIFIER) {
                if (currentType == TokenType.OPERATOR) {
                continue;
            }
            else if ((baseType == TokenType.FLOAT && currentType == TokenType.INTEGER) ||
                    (baseType == TokenType.INTEGER && currentType == TokenType.FLOAT)) {
                continue;
            }
            if (currentType != baseType) {
                //TODO : throw the good TypeErrorException
                throw new Exception("TypeError: mismatched types in constant declaration (" + currentType + " vs " + baseType + ")   Valeur :" + expression.getValue() + "vs" + baseType);
            }
            }
        }
    }


}
