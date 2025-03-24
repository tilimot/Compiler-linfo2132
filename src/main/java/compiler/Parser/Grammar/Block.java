package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Block {
    String leftBracket;
    ArrayList<Statement> statements;
    String rightBracket;

    public Block(String leftBracket, ArrayList<Statement> statements, String rightBracket){
        this.leftBracket = leftBracket;
        this.statements = statements;
        this.rightBracket = rightBracket;
    }
}
