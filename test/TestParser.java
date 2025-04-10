import static org.junit.Assert.*;

import compiler.Lexer.Lexer;
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
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        ArrayList<Expression> expressions = parser.parseExpressions();
        assertNotNull(expressions);
    }

    @Test
    public void testExpressionsLength() throws Exception{
        String input = "a+2*'hello'/true";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        ArrayList<Expression> expressions = parser.parseExpressions();
        assertEquals(7,expressions.size());
    }

    @Test
    public void test_ShouldReturn_MethodCall_Object() throws Exception{
        String input = "a(b,'hello', true);";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        Statement stmt = parser.parseCallOrDeclarationOrAssignment();
        assertTrue(stmt instanceof MethodCall);
    }

    @Test
    public void test_ShouldReturn_AssignmentStatement_Object() throws Exception{
        String input = "a int = 1+/3*'hello'-true;";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        Statement stmt = parser.parseCallOrDeclarationOrAssignment();
        assertTrue(stmt instanceof AssignementStatement);
    }

}
