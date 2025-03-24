package compiler.Parser.Grammar;

import java.util.ArrayList;

public class File {
    ArrayList<Statement> statements;

    public File (ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            sb.append(statement.toString()).append("\n");
        }
        return sb + "\n";
    }


}
