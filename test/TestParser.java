import static org.junit.Assert.*;

import compiler.Lexer.Lexer;
import compiler.Lexer.TokenType;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import compiler.Parser.Parser;
import compiler.Parser.Grammar.*;

public class TestParser {

    @Test
    public void testBasicInput() throws Exception {
        String input = "a+2*'hello'/true";
        StringReader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        Parser parser = new Parser(lexer);

        ArrayList<Expression> expressions = parser.parseExpressions();
        assertNotNull(expressions);

    }

}
