package compiler.Parser.Grammar;

import java.util.HashMap;

public class RecordDeclaration extends Statement {
        String identifier;

        public RecordDeclaration(String identifier, int tabIndex) {
            super(tabIndex);
            this.identifier = identifier;
        }

    @Override
    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {

    }
}
