package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Block {
    String leftBracket;
    ArrayList<Statement> statements;
    String rightBracket;
    int tabIndex;


    public Block(String leftBracket, ArrayList<Statement> statements, String rightBracket, int tabIndex){
        this.leftBracket = leftBracket;
        this.statements = statements;
        this.rightBracket = rightBracket;
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            sb.append(statement.toString()).append("\n").append(statement.tabIndex);
        }
        return t+"BLOCK : "+"\n"+t+leftBracket + "\n"+t + sb + rightBracket+ "\n";
    }
}
