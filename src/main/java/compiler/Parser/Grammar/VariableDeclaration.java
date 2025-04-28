package compiler.Parser.Grammar;

import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class VariableDeclaration extends Statement {
    String identifier;
    ArrayList<Type> type;
    String eol;


    public VariableDeclaration(String identifier, ArrayList<Type> type, String eol, int tabIndex){
        super(tabIndex);
        this.identifier = identifier;
        this.type = type;
        this.eol= eol;

    }

    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();
        for (Type type : type) {
            if (type instanceof SimpleType) {
                ((SimpleType) type).tabIndex = tabIndex;
            } else if (type instanceof ArrayDeclarationBracket) {
                ((ArrayDeclarationBracket) type).tabIndex = tabIndex;
            }
            typeStr.append(type);
        }
        return t+identifier + "\n" + typeStr + "\n" + t + eol + "\n";
    }

    @Override
    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {

    }
}
