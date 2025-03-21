package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class Param extends Matcher {

    public Param(Parser parser) {
        super(parser);
    }

    public Param parseParam(){
        // TODO logic for parsing parameters
        return new Param(parser);
    }
}
