package compiler.Parser.Grammar;

import compiler.Semantic.SymbolTable;

import java.util.HashMap;

public abstract class Statement {
    int tabIndex;
    public Statement(int tabIndex){
        this.tabIndex = tabIndex;
    }

    public abstract void semanticAnalysis(HashMap<String, Type> st) throws Exception;


}
