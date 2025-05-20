package compiler.Parser.Grammar;

import compiler.Semantic.SymbolTable;

import java.util.HashMap;

public abstract class Statement {
    int tabIndex;
    public Statement(int tabIndex){
        this.tabIndex = tabIndex;
    }

    public static boolean iAssignStatement(Statement stmt){
        return stmt instanceof  AssignementStatement;
    }

    public static boolean isVarAssignStatement(AssignementStatement statement){ return (statement.leftSide instanceof LeftSideAssignement) && (statement.leftSide.getType() != null) ;}

    public static boolean isVarReassignStatement(AssignementStatement statement){ return (statement.leftSide instanceof LeftSideAssignement) && (statement.leftSide.getType() == null) ;}

    public static boolean isWhileStatement(Statement stmt){ return stmt instanceof WhileStatement;}

    public static boolean isIfStatement(Statement stmt){return stmt instanceof IfStatement; }

    public static boolean isElseStatement(Statement stmt){return stmt instanceof ElseStatement; }


    public static boolean isReturnStatement(Statement stmt){ return  stmt instanceof ReturnStatement;}

    public abstract void semanticAnalysis(SymbolTable symbolTable) throws Exception;


}
