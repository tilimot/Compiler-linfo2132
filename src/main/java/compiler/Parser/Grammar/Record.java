package compiler.Parser.Grammar;

import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Record {
    String recordsName;
    String rec_;
    String openingBracket;
    ArrayList<VariableDeclaration> declaration;
    String closingBracket;
    int tabIndex;

    public Record(String recordsName, String rec_, String openingBracket, ArrayList<VariableDeclaration> declaration, String closingBracket,int tabIndex) {
        this.recordsName = recordsName;
        this.rec_ = rec_;
        this.openingBracket = openingBracket;
        this.declaration = declaration;
        this.closingBracket = closingBracket;
        this.tabIndex = tabIndex;
    }

    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex + 1);
        StringBuilder declarationStr = new StringBuilder();
        for (VariableDeclaration varDecl : declaration) {
            varDecl.tabIndex = tabIndex + 2;
            declarationStr.append(varDecl.toString());
        }
        return t + "RECORD : " + "\n" + tNext + recordsName + "\n" + tNext + rec_ + "\n" + tNext +
                openingBracket + "\n" + tNext + "DECLARATION : " + "\n" +
                declarationStr + tNext + closingBracket + "\n";
    }

    public void semanticAnalysis(SymbolTable symbolTable) throws Exception {
        symbolTable.getParentTable().getTable().put(recordsName, new SimpleType(recordsName, tabIndex));
        for (VariableDeclaration varDecl : declaration) {
            varDecl.semanticAnalysis(symbolTable);
        }
    }
}
