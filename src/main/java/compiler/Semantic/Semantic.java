package compiler.Semantic;

import compiler.Compiler;
import compiler.Exception.*;
import compiler.Lexer.BuildIn;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;



public class Semantic {

    public Ast ast;
    public static SymbolTable symbolTable;

    public Semantic(Ast ast ){
        this.ast = ast;
        symbolTable = new SymbolTable(null,"root");


    }

    public void startAnalysis () throws Exception {
        ast.semanticAnalysis(symbolTable);
    }

    public static TokenType checkType(String expression_value) {

        if (expression_value.contains("\""))
            return TokenType.STRINGS;
        if (List.of(
                "readInt", "readFloat", "readString",
                "writeInt", "writeFloat", "write", "writeln").contains(expression_value)) {
            return TokenType.BUILDIN;
        }

        if (expression_value.contains("true") || expression_value.contains("false"))
            return TokenType.BOOLEAN;
        if (expression_value.equals("Array"))
            return TokenType.ARRAY;
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
                    (baseType == TokenType.INTEGER && t == TokenType.FLOAT|| t== TokenType.ARRAY ||t ==  TokenType.BUILDIN)) {
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
        if (!fdType.getValue().equals("int") && !fdType.getValue().equals("float") && !fdType.getValue().equals("string") && !fdType.getValue().equals("bool") && Character.isLowerCase(fdType.getValue().charAt(0))) {
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

    public static void checkGlobalDecl(AssignementStatement global, SymbolTable symbolTable) throws Exception {

        boolean initialized = false;
        String id = global.leftSide.getIdentifier();

        Type type;
        if (global.leftSide.getType() == null) {
            type = symbolTable.getSymbol(id,symbolTable);
            initialized = true;
        }
        else {
            type = global.leftSide.getType();
        }
        if (!initialized && symbolTable.containsSymbol(id) && symbolTable.getName().equals("root")) {
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
    public static void checkParams(SymbolTable st, Expression expression) throws Exception {
        if (expression.params.size() != st.getTable().size()) {
            throw new Exception("TypeError: Number of parameters in record access is not correct");
        }
        int i = 0;
        for (Type type : st.getTable().values()) {
            for (int j = 0; j < expression.params.get(i).expressions.size(); j++) {
                TokenType type1 = expression.params.get(i).expressions.get(j).getType();
                if (type1 == TokenType.IDENTIFIER) {
                    int size = st.getParentTable().getChildTable().size();
                    boolean inFunc = st.getParentTable().getChildTable().get(size-1).getTable().containsKey(expression.params.get(i).expressions.get(j).getValue());
                    if (inFunc)
                        type1 = st.getParentTable().getChildTable().get(size-1).getTable().get(expression.params.get(i).expressions.get(j).getValue()).getType();
                    else {
                        var entry = st.getParentTable().getTable().get(expression.params.get(i).expressions.get(j).getValue());
                        if (entry != null) {
                            type1 = entry.getType();
                        } else {
                            throw new ScopeException(expression.params.get(i).expressions.get(j).getValue());
                        }
                    }
                }
                if (type1 != type.getType() && type1 != TokenType.OPERATOR && type1 != TokenType.ARRAY) {
                    throw new InvalidParametersException(expression.params.get(i).expressions.get(j).getValue(), type.getValue());
                }
            }
            i++;
        }
    }

    public static void checkReturn(Type returnType, ArrayList<Expression> expressions) throws Exception {
        ArrayList<Type> returnTypes  = new ArrayList<>();
        returnTypes.add(returnType);
        checkFinalDecl(new Constant("final", "return", returnTypes, "=", expressions, ";", 0));
    }

    public static void checkRefToVariable(String identifier, ArrayList<Expression> expressions, SymbolTable symbolTable) throws Exception {
        for (Expression expression : expressions) {
            if(checkType(expression.getValue()) == TokenType.IDENTIFIER) {
                if (!symbolTable.containsSymbol(expression.getValue())) {
                    throw new ScopeException(expression.getValue());
                }
                Type identifierSymbol = symbolTable.getSymbol(identifier, symbolTable);
                if (identifierSymbol == null && symbolTable.getParentTable() != null) {
                    identifierSymbol = symbolTable.getParentTable().getSymbol(identifier, symbolTable.getParentTable());
                }

                Type expressionSymbol = symbolTable.getSymbol(expression.getValue(), symbolTable);


                if (!identifierSymbol.getType().equals(expressionSymbol.getType())) {
                    throw new VariableException(identifierSymbol, expressionSymbol);
                }

                if (identifierSymbol.getValue().equals("void")) {
                    throw new Exception("TypeError: Type of final var should not be void");
                }

                if (identifierSymbol.getType() == TokenType.RECORD_NAME || expression.params != null) {

                    SymbolTable currentTable = symbolTable;
                    while (currentTable.getParentTable() != null) {
                        currentTable = currentTable.getParentTable();
                    }
                    for (SymbolTable st : currentTable.getChildTable()) {

                        if (st.getName().equals(symbolTable.getSymbol(identifier, symbolTable).getValue())) {
                            Semantic.checkParams(st, expression);
                        }
                        else if (st.getName().equals(expression.getValue())) {
                            st = st.getChildTable().getFirst();
                            Semantic.checkParams(st, expression);
                        }
                    }


                }


            }
        }

    }
}
