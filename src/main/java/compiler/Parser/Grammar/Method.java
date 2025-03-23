package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class Method extends Matcher {

    public Method(Parser parser) {
        super(parser);
    }

    Method parseMethod() {
        return new Method(parser);
    }
}
