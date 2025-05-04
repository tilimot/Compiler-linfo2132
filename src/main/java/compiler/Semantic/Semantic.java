package compiler.Semantic;

import compiler.Exception.*;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;

import java.util.ArrayList;
import java.util.Optional;

public class Semantic {

    public Ast ast;
    public static SymbolTable symbolTable;

    public Semantic(Ast ast ){
        this.ast = ast;
        symbolTable = new SymbolTable(null);

    }

    public void startAnalysis () throws Exception {
        ast.semanticAnalysis(SymbolTable.getTable());
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
        if (expressions.isEmpty()) return false;
        ArrayList<TokenType> resolvedTypes = new ArrayList<>();
        for (TokenType t : expressions) {
            if (t != TokenType.IDENTIFIER && t != TokenType.OPERATOR) {
                resolvedTypes.add(t);
            }
        }
        if (resolvedTypes.isEmpty()) return false;

        TokenType baseType = resolvedTypes.getFirst();

        for (TokenType t : resolvedTypes) {
            if ((baseType == TokenType.FLOAT && t == TokenType.INTEGER) ||
                    (baseType == TokenType.INTEGER && t == TokenType.FLOAT)) {
                continue;
            }
            if (t != baseType) {
                return true;
            }
        }
        return false;
    }

    public static void checkFinalDecl(Constant constant) throws Exception {
        if (symbolTable.containsSymbol(constant.identifier))
            throw new DuplicateException(constant.identifier);
        Type fdType = constant.basetype.getFirst();
        if (fdType.getValue().equals("void")){
            throw new Exception("TypeError: Type of final var should not be void");
        }
        if (!fdType.getValue().equals("int") && !fdType.getValue().equals("float") && !fdType.getValue().equals("string") && !fdType.getValue().equals("bool")){
            throw new Exception("TypeError: Type of final var should be a base type.");
        }
        ArrayList<TokenType> expressionType = new ArrayList<>();
        for (Expression expression : constant.expressions) {
            expressionType.add(expression.getType());
        }
        if (Semantic.checkExpressionsType(expressionType)) {
            throw new OperatorException();
        }
        expressionType.addFirst(constant.basetype.getFirst().getType());
        if (Semantic.checkExpressionsType(expressionType)) {
            throw new TypeException();
        }
    }

    public static void checkGlobalDecl(AssignementStatement global) throws Exception {

        boolean initialized = false;
        String id = global.leftSide.getIdentifier();

        Type type;
        if (global.leftSide.getType() == null) {
            type = symbolTable.getSymbol(id);
            initialized = true;
        }
        else {
            type = global.leftSide.getType();
        }

        if (!initialized && symbolTable.containsSymbol(id)) {
            throw new DuplicateException(id);
        }

        if (type.getValue().equals("void")) {
            throw new Exception("TypeError: Type of final var should not be void");
        }

        ArrayList<TokenType> expressionType = new ArrayList<>();
        if (global.rightSide instanceof RightSideExpressions) {
            for (Expression expression : ((RightSideExpressions) global.rightSide).expressions) {
                expressionType.add(expression.getType());
            }
        }

        if (Semantic.checkExpressionsType(expressionType)) {
            throw new OperatorException();
        }

        if (global.leftSide instanceof LeftSideAssignement) {
            expressionType.addFirst(type.getType());
        }

        if (Semantic.checkExpressionsType(expressionType)) {
            throw new TypeException();
        }
    }


    public static void checkRefToVariable(String identifier, ArrayList<Expression> expressions) throws Exception {
        for (Expression expression : expressions) {
            if(checkType(expression.getValue()) == TokenType.IDENTIFIER){
                if (!symbolTable.containsSymbol(expression.getValue())){
                    throw new ScopeException(expression.getValue());
                }
                System.out.println(symbolTable.getSymbol(identifier)+ " and "+symbolTable.getSymbol(expression.getValue()));

                if (!symbolTable.getSymbol(identifier).getType().equals(symbolTable.getSymbol(expression.getValue()).getType())){
                    throw new VariableException(symbolTable.getSymbol(identifier),symbolTable.getSymbol(expression.getValue()));
                }
            }
        }
    }
}
