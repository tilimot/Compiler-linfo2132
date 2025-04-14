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

    public static boolean checkExpressionsType(ArrayList<TokenType> expressions) throws Exception {
        if (expressions.isEmpty()) return true;
        ArrayList<TokenType> resolvedTypes = new ArrayList<>();
        for (TokenType t : expressions) {
            if (t != TokenType.IDENTIFIER && t != TokenType.OPERATOR) {
                resolvedTypes.add(t);
            }
        }
        if (resolvedTypes.isEmpty()) return true;

        TokenType baseType = resolvedTypes.getFirst();

        for (TokenType t : resolvedTypes) {
            if ((baseType == TokenType.FLOAT && t == TokenType.INTEGER) ||
                    (baseType == TokenType.INTEGER && t == TokenType.FLOAT)) {
                continue;
            }
            if (t != baseType) {
                return false;
            }
        }
        return true;
    }
}
