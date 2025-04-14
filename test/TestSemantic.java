import compiler.Exception.MissingConditionException;
import compiler.Lexer.Lexer;
import compiler.Parser.Parser;
import compiler.Parser.Semantic;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class TestSemantic {

    public Parser getParser(String input) {
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        return new Parser(lexer);
    }

    @Test
    public void ifLoops_WithoutCondition_Should_Throws_MissingConditionError() throws Exception {
        String input = "if(){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithoutCondition_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void whileLoops_WithoutCondition_Should_Throws_MissingConditionError() throws Exception {
        String input = "while(){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithMissingCondition_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(,1,110,1){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithMissingCondition2_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(i,,110,1){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }
    @Test
    public void forLoops_WithMissingCondition3_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(i,1,,1){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithMissingCondition4_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(i,1,110,){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithMissingCondition5_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(i,1){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void forLoops_WithMissingCondition6_Should_Throws_MissingConditionError() throws Exception {
        String input = "for(i,1,){}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }

    @Test
    public void badAssignmentType() throws Exception {
        String input = "a string = 1;";
        Parser parser = getParser(input);
        Semantic semantic = new Semantic(parser.getAST());
        try {
            parser.getAST();
            fail("MissingConditionError wasn't raise");
        } catch (MissingConditionException e) {
            // Assert
            assertEquals("MissingConditionError: Must attribute a condition", e.getMessage());
        }
    }



}
