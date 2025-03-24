package compiler.Parser.Grammar;

import java.util.ArrayList;

public class File {
    ArrayList<Statement> statements;
    int tabIndex;

    public File (ArrayList<Statement> statements, int tabIndex) {
        this.statements = statements;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Statement statement : statements) {
            sb.append(statement.toString()).append("\n");
        }
        return "\n".repeat(tabIndex) +sb + "\n";
    }


}
