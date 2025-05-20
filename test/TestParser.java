import static org.junit.Assert.*;

import compiler.Lexer.Lexer;
import compiler.Parser.Grammar.Record;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import compiler.Parser.Parser;
import compiler.Parser.Grammar.*;

public class TestParser {

    private Parser getParser(String input) {
        Reader reader = new StringReader(input);
        Lexer lexer = new Lexer(reader);
        return new Parser(lexer);
    }

    @Test
    public void testBasicInput() throws Exception {
        String input = "b = a+2*'hello'/true";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        ArrayList<Expression> expressions = parser.parseExpressions();
        assertNotNull(expressions);
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
        String input = "a int = 1+b/3*'hello'-true;";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        Statement stmt = parser.parseCallOrDeclarationOrAssignment();
        assertTrue(stmt instanceof AssignementStatement);
    }

    @Test
    public void test_Shouldreturn_ArrayType_CorrectLength() throws Exception{
        String input = "int []";
        System.out.println(input);
        Reader reader = new StringReader(input);

        Lexer lexer = new Lexer(reader);
        System.out.println(lexer);
        Parser parser = new Parser(lexer);

        ArrayList<Type> type = parser.parseType();
        assertEquals(2,type.size());
    }


        @Test
        public void testFinalVariableDeclarations() throws Exception {
            String[] decls = {
                    "final i int = 3;",
                    "final j float = 3.2*5.0;",
                    "final k int = i*3;",
                    "final message string = \"Hello\";",
                    "final isEmpty bool = true;"
            };
            for (String decl : decls) {
                Parser parser = getParser(decl);
                Constant stmt = parser.parseConstant();
                assertNotNull("La déclaration n'a pas été parsée: " + decl, stmt);

            }
        }

        // 2) Déclarations de record
        @Test
        public void testRecordDeclarations() throws Exception {
            String[] records = {
                    "Point rec { x int; y int; }",
                    "Person rec{ name string; location Point; history int[]; }"
            };
            for (String rec : records) {
                Parser parser = getParser(rec);
                Statement stmt = parser.parseRecord();
                assertNotNull("Le record n'a pas été parsé: " + rec, stmt);
                assertTrue("Doit être une déclaration de record",
                        stmt instanceof Record);
            }
        }


        // 4) Parsing complet du "fichier final"
        @Test
        public void testFullFileParsing() throws Exception {
            String program = ""
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
                    + "Person rec{ name string; location Point; history int[]; }\n"
                    + "a int = 3;\n"
                    + "c int[] = array [5] of int;\n"
                    + "d Person= Person(\"me\", Point(3,7), array [i*2] of int);\n"
                    + "fun square(v int) int { return v*v; }\n"
                    + "fun copyPoints(p Point[]) Point { return Point(p.x+p.x, p.y+p.y); }\n"
                    + "fun main() {\n"
                    + "    value int = readInt();\n"
                    + "    writeln(square(value));\n"
                    + "    i int;\n"
                    + "    for (i, 1, 100, 1) { while (value != 3) { } }\n"
                    + "    i = (i+2)*2;\n"
                    + "}\n";

            Parser parser = getParser(program);
            // getAST() fait tout parser (déclarations + fonctions + statements)
            assertNotNull("L'AST complet n'a pas pu être créé", parser.getAST());
        }

        // 5) Cas d'expression tirés du fichier final
        @Test
        public void testExpressionParsingFromFinalFile() throws Exception {
            // Exemple d'expression plus complexes
            String[] exprs = {
                    "b = 3.2*5.0",
                    "c = i*3",
                    "v = array [i*2] of int",
                    "g = Point(3,7)",
                    "i = writeln(square(value))"
            };
            for (String e : exprs) {
                Parser parser = getParser(e);
                ArrayList<Expression> list = parser.parseExpressions();
                assertFalse("L'expression n'a pas été parsée: " + e, list.isEmpty());
            }
        }
    }




