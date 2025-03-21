package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class Comment extends Matcher {

    public Comment(Parser parser) {
        super(parser);
    }

    public Comment parseComment() {
        // TODO logic for parsing comments
        return new Comment(parser);
    }
}
