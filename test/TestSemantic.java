import compiler.Exception.MissingConditionException;
import compiler.Exception.OperatorException;
import compiler.Exception.TypeException;
import compiler.Lexer.Lexer;
import compiler.Parser.Grammar.Ast;
import compiler.Parser.Parser;
import compiler.Semantic.SymbolTable;
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
    SymbolTable symbolTable = new SymbolTable(null, "root");


    @Test
    public void ifLoops_WithoutCondition_Should_Throws_MissingConditionError() throws Exception {
        String testInput = "if(){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "while(){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(,1,110,1){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(i,,110,1){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(i,1,,1){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(i,1,110,){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(i,1){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
        String testInput = "for(i,1,){}";
        String input = "fun testFunc()int{"+testInput+"}";
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
    public void forLoops_Should_Not_Throws_MissingConditionError() throws Exception{
        String testInput = "for(i,1,100,1){}";
        String input = "fun testFunc()int{"+testInput+"}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
        } catch (MissingConditionException e) {
            fail("Missing condition should not be raised");
        }
    }

    @Test
    public void whileLoops_Should_Not_Throws_MissingConditionError() throws Exception{
        String testInput = "while(i>2){}";
        String input = "fun testFunc()int{"+testInput+"}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
        } catch (MissingConditionException e) {
            fail("Missing condition should not be raised");
        }
    }

    @Test
    public void ifStmnt_Should_Not_Throws_MissingConditionError() throws Exception{
        String testInput = "if(a){}";
        String input = "fun testFunc()int{"+testInput+"}";
        Parser parser = getParser(input);
        try {
            parser.getAST();
        } catch (MissingConditionException e) {
            fail("Missing condition should not be raised");
        }
    }


    @Test
    public void semanticAnalysis_Should_Pass_On_IntPlusInt() throws Exception {
        String input = "x int = 3 + 4;";
        Parser parser = getParser(input);
        parser.getAST(); // ne doit pas lever d’exception
    }

    @Test(expected = OperatorException.class)
    public void semanticAnalysis_Should_Throw_OperatorError_On_IntPlusString() throws Exception {
        String input = "x int  = 3 + \"hello\";";
        Parser parser = getParser(input);
        Ast ast = parser.getAST();
        ast.semanticAnalysis(symbolTable);
    }



    @Test(expected = OperatorException.class)
    public void semanticAnalysis_Should_Throw_OperatorError_On_MixedTypesWithIdentifier() throws Exception {
        String input = " x int= 5 + \"abc\" + y;";

        Parser parser = getParser(input);
        Ast ast = parser.getAST();
        ast.semanticAnalysis(symbolTable); // Doit lever OperatorException
    }


    @Test(expected = TypeException.class)
    public void semanticAnalysis_Should_Throw_TypeError_On_AssignmentMismatch() throws Exception {
        String input = " x int = \"hello\";";
        Parser parser = getParser(input);
        Ast ast = parser.getAST();
        ast.semanticAnalysis(symbolTable); // Doit lever TypeException
    }
    @Test
    public void testFunctionCallWithCorrectTypes() throws Exception {
        String input = " Point rec { x int; y int;} fun square(v Point, p int) string { return \"ok\";} fun main()" +
                " { c string = \"hello\";a Point = Point(1,2); b string = square(a, 3);} ";
        Parser parser = getParser(input);
        Ast ast = parser.getAST();
        ast.semanticAnalysis(new SymbolTable(null,"root"));
    }




}
