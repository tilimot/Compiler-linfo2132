import compiler.Lexer.Lexer;
import compiler.Parser.Grammar.*;
import compiler.Parser.Parser;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestSemantic {
    public Parser getParser(String input){
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        return new Parser(lexer);
    }

    @Test
    public void testBasicInput() throws Exception {
        String input = "a+2*'hello'/true";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        ArrayList<Expression> expressions = parser.parseExpressions();
        assertNotNull(expressions);
    }

}
