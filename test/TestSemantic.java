import compiler.Exception.*;
import compiler.Lexer.Lexer;
import compiler.Parser.Grammar.Ast;
import compiler.Parser.Parser;
import compiler.Semantic.Semantic;
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
                " { c string = \"hello\";a Point = Point(1,2); square(a, 3);} ";
        Parser parser = getParser(input);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();
    }

    private String getFullProgram() {
        return ""
                + "final i int = 3;\n"
                + "final j float = 3.2*5.0;\n"
                + "final k int = i*3;\n"
                + "final message string = \"Hello\";\n"
                + "final isEmpty bool = true;\n"
                + "\n"
                + "Point rec {\n"
                + "    x int;\n"
                + "    y int;\n"
                + "}\n"
                + "Person rec{\n"
                + "    name string;\n"
                + "    location Point;\n"
                + "    history int[];\n"
                + "}\n"
                + "\n"
                + "a int = 3;\n"
                + "c int[] = array [5] of int;\n"
                + "d Person = Person(\"me\", Point(3,7), array [i*2] of int);\n"
                + "\n"
                + "fun square(v int) int {\n"
                + "    return v*v;\n"
                + "}\n"
                + "\n"
                + "fun copyPoints(p Point[]) Point {\n"
                + "    return Point(p.x+p.x, p.y+p.y);\n"
                + "}\n"
                + "\n"
                + "fun main() {\n"
                + "    value int = readInt();\n"
                + "    writeln(square(value));\n"
                + "    i int;\n"
                + "    for (i, 1, 100, 1) {\n"
                + "        while (value != 3) {\n"
                + "        }\n"
                + "    }\n"
                + "    i = (i+2)*2;\n"
                + "}\n";
    }

    @Test
    public void fullProgram_Should_Not_Throw_AnyException() throws Exception {
        Parser parser = getParser(getFullProgram());
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();
    }

    @Test
    public void arrayAndRecordDeclarations_Should_Be_Accepted() throws Exception {
        // On ne s'intéresse qu'aux déclarations de c, d et Person/Point
        String input = ""
                + "Point rec { x int; y int; }\n"
                + "Person rec{ name string; location Point; history int[]; }\n"
                + "c int[] = array [5] of int;\n"
                + "d Person = Person(\"me\", Point(3,7), array [i*2] of int);\n";
        Parser parser = getParser(input);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();
    }

    @Test
    public void loopsAndAssignments_Should_Be_Accepted() throws Exception {
        // On teste uniquement la boucle imbriquée et l'affectation finale
        String input = ""
                + "fun main() {\n"
                + "    i int;\n"
                + "    for(i,1,10,1) {\n"
                + "        while(i != 5) {}\n"
                + "    }\n"
                + "    i = (i+2)*2;\n"
                + "}\n";
        Parser parser = getParser(input);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();
    }



    @Test(expected = TypeException.class)
    public void assignmentOfFloatToInt_Should_Throw_TypeException() throws Exception {
        // Affectation directe d'un float à un int
        String input = "x int = \"hello\";";
        Parser parser = getParser(input);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();
    }

    @Test(expected = InvalidParametersException.class)

    public void InvalidParametersRecord()throws Exception{
        String snippet = "Point rec {\n" +
                "x int;\n" +
                "y int;\n" +
                "}\n" +
                "\n" +
                "myrec Point = Point(1,\"2\");";
        Parser parser = getParser(snippet);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();

    }
    @Test(expected = InvalidParametersException.class)

    public void InvalidParametersFunction()throws Exception{
        String snippet = "fun square(v int, p string) string{\n" +
                "return \"ok\";\n" +
                "}\n" +
                "\n" +
                "fun main(){\n" +
                "mot string = square(1, 1);\n" +
                "}";
        Parser parser = getParser(snippet);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();

    }
    @Test(expected = VariableException.class)
    public void InvalidReturnFunction()throws Exception{
        String snippet = "fun square(v int, p int) string{\n" +
                "return \"ok\";\n" +
                "}\n" +
                "\n" +
                "fun main(){\n" +
                "mot int = square(1, 1);\n" +
                "}";
        Parser parser = getParser(snippet);
        Ast myAst = parser.getAST();

        Semantic semantic = new Semantic(myAst);
        semantic.startAnalysis();

    }

}
