package compiler.Parser.Grammar;

import java.util.ArrayList;
import java.util.HashMap;

public class Block {
    String leftBracket;
    ArrayList<Statement> statements;
    String rightBracket;
    int tabIndex;
    String eol;


    public Block(String leftBracket, ArrayList<Statement> statements, String rightBracket, int tabIndex){
        this.leftBracket = leftBracket;
        this.statements = statements;
        this.rightBracket = rightBracket;
        this.tabIndex = tabIndex;
    }

    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {
        for (Statement statement : statements) {
            statement.semanticAnalysis(st);
        }
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            statement.tabIndex = tabIndex+1;
            sb.append(statement);
        }
        return t+"BLOCK : "+"\n"+tNext +leftBracket + "\n"+ sb + tNext+ rightBracket+ "\n";
    }
}
