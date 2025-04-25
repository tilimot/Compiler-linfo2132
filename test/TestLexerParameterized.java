import static org.junit.Assert.*;

import compiler.Lexer.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;
import static org.hamcrest.CoreMatchers.is;

import java.util.Collection;
import java.util.Arrays;

import java.io.Reader;
import java.io.StringReader;
import compiler.Lexer.Lexer;
import compiler.Lexer.Tokenizer;

@RunWith(value = Parameterized.class)
public class TestLexerParameterized {

    @Parameter(value=0)
    public String input;

    @Parameter(value=1)
    public String expected;

    public class splitter{
        private String input;

        public static String splitIntoToken(String input){
            StringReader reader = new StringReader(input);
            Tokenizer t = new Tokenizer(reader);
            return t.splitIntoFloatOrIntToken(input, 0).getToken().toString();
        }
    }

    // name attribute is optional, provide an unique name for test
    // multiple parameters, uses Collection<Object[]>
    @Parameters(name = "{index}: splitIntoToken({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0.3123","0.3123"},
                {"1.112", "1.112"},
                {".123","0.123"},
                {"0.3123ab","0.3123"},
                {"1.112ab", "1.112"},
                {".123ab","0.123"},
                {"0000","0"},
                {"01234","1234"},
                {"000.123","0.123"},
                {"0001.1234","1.1234"},
                {"4567","4567"},
                {"789ab","789"},
                {".ab", "0."},
                {"1.a", "1."}
        });
    }

    @Test
    public void test_splitIntoTokenIntFloat() {
        assertEquals(expected,splitter.splitIntoToken(input));
    }
}
