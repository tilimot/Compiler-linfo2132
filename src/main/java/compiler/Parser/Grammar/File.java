package compiler.Parser.Grammar;

import java.util.ArrayList;

public class File {
    ArrayList<Statement> statements;

    public File (ArrayList<Statement> statements) {
        this.statements = statements;
    }
}
