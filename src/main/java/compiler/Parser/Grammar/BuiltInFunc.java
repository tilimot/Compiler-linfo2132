package compiler.Parser.Grammar;

import compiler.Parser.Matcher;
import compiler.Parser.Parser;

public class BuiltInFunc extends Matcher {
    /*
    !(bool): negates a boolean value, returns a boolean value
     chr(int) : turns the character (an int value) into a string, returns a string
     len(string or array)  : gives the length of a string or array, returns an integer
     floor(float) : returns the largest integer less than or equal the float value
     */
    public BuiltInFunc(Parser parser) {
        super(parser);
    }
    public BuiltInFunc parseBuiltInFunc() {
        //TODO logic for parsing type
        return new BuiltInFunc(parser);
    }
}
