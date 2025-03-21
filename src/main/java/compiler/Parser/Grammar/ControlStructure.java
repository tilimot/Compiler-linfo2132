package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class ControlStructure extends Matcher {

    public ControlStructure(Parser parser) {
        super(parser);
    }

    public ControlStructure parseControlStructure() {
        // TODO logic for parsing control structures
        return new ControlStructure(parser);
    }
}
