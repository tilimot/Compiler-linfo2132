package compiler.Parser.Grammar;

import java.util.ArrayList;

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

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            statement.tabIndex = tabIndex+1;
            sb.append(statement.toString());
        }
        return t+"BLOCK : "+"\n"+tNext +leftBracket + "\n"+ sb + tNext+ rightBracket+ "\n";
    }
}
