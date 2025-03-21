package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class Return extends Matcher {

    public Return(Parser parser) {
        super(parser);
    }

    public Return parseReturn() {
        // TODO logic for parsing return statements
        return new Return(parser);
    }
}
