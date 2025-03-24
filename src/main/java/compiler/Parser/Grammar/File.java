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
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        for (Statement statement : statements) {
            statement.tabIndex = tabIndex+1;
            sb.append(statement.toString());
        }
        return t+"Root :"+ "\n" +sb + "\n";
    }


}
