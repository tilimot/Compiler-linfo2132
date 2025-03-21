package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class Operation extends Matcher {

    public Operation(Parser parser) {
        super(parser);
    }

    public Operation parseOperation() {
        // TODO logic for parsing operations
        return new Operation(parser);
    }
}
