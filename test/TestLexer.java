import static org.junit.Assert.*;

import compiler.Lexer.TokenType;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import compiler.Lexer.Lexer;

public class TestLexer {

    @Test
    public void testBasicInput() {
        String input = "var x int = 2;";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

}
