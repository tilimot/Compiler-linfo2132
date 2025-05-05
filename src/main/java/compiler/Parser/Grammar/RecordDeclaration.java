package compiler.Parser.Grammar;

import java.util.HashMap;

public class RecordDeclaration extends Statement {
        String identifier;
        String type;

        public RecordDeclaration(String identifier,Type type, int tabIndex) {
            super(tabIndex);
            this.identifier = identifier;
            this.type = type.toString();
        }

    @Override
    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {
    }

    @Override
    public String toString() {
            String t = "\t".repeat(tabIndex+1);

            return t + identifier + "\n" + t + type + "\n";
    }
}
