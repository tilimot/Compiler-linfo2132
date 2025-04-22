import static org.junit.Assert.*;

import compiler.Lexer.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;

import java.io.Reader;
import java.io.StringReader;

import compiler.Lexer.Lexer;
import compiler.Lexer.Tokenizer;

public class TestLexer {

    @Test
    public void testBasicInput() {
        String input = "1.2";
        StringReader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        assertNotNull(lexer.getNextSymbol());
    }

    @Test
    public void test() {
        String input = "a int= 2.4;";
        StringReader reader = new StringReader(input);
        Tokenizer t = new Tokenizer(reader);
        List<String> result = t.splitIntoTokens(input);
        List<String> expected = List.of("a","int","=","2.4",";");

        assertEquals(expected,result);
    }
}
