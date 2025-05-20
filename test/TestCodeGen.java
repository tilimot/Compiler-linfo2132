
//package

import compiler.Lexer.FileToReader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import compiler.Lexer.*;
import compiler.CodeGeneration.*;
import compiler.Parser.*;
import compiler.Parser.Grammar.Ast;
import java.lang.reflect.Field;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;


class CustomClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}

public class TestCodeGen {

    private String testClassName;
    private CustomClassLoader classLoader;

    @Before
    public void setUp() {
        testClassName = "GeneratedTestClass_" + System.nanoTime();
        classLoader = new CustomClassLoader();
    }


    private Class<?> compileAndLoad(String fileName) throws Exception {
        String sourceFilePath = "./test/resources/code/" + fileName;
        Reader readerFile = FileToReader.getReaderFromFile(sourceFilePath);
        Lexer lexer = new Lexer(readerFile);
        Parser parser = new Parser(lexer);
        Ast ast = parser.getAST();

        CodeGenerator codeGenerator = new CodeGenerator(testClassName, ast);
        codeGenerator.generateFileClass();
        byte[] bytecode = codeGenerator.getCw().toByteArray();

        return classLoader.defineClass(testClassName, bytecode);
    }

    @Test
    public void testBasicAdditionFromFile() throws Exception {


        Class<?> generatedClass = compileAndLoad("basic_addition.txt");

        Constructor<?> constructor = generatedClass.getDeclaredConstructor();

        Object instance = constructor.newInstance();
        Method mainMethod = generatedClass.getMethod("test");
        int result = (int) mainMethod.invoke(instance);

        assertEquals(8, result);
    }



    @Test
    public void testConstantsFromFile() throws Exception {
        Class<?> generatedClass = compileAndLoad("constants.txt");


        Field iField = generatedClass.getField("i"); // getField pour les champs publics
        int iValue = iField.getInt(null); // get(null) pour les champs statiques
        assertEquals("final i should equals 3", 3, iValue);

        Field kField = generatedClass.getField("k");
        int kValue = kField.getInt(null);
        assertEquals("constant k should be equal to 9(=i*3)", 9, kValue);

        Field messageField = generatedClass.getField("message");
        String messageValue = (String) messageField.get(null);
        assertEquals("constant message should be equal to \"Hello\"", "\"Hello\"", messageValue);

        Field isEmptyField = generatedClass.getField("isEmpty");
        boolean isEmptyValue = isEmptyField.getBoolean(null);
        assertEquals("constant isEmpty should be true", true, isEmptyValue);
    }


    @Test
    public void testGlobalVariables() throws Exception {
        Class<?> generatedClass = compileAndLoad("globVar.txt");

        Field eField = generatedClass.getField("e");
        int eValue = eField.getInt(null);
        assertEquals("Variable 'e' should be 81", 81, eValue);

        Field aField = generatedClass.getField("a");
        int aValue = aField.getInt(null);
        assertEquals("Variable 'a' should be 82", 82, aValue);

        Field cField = generatedClass.getField("c");
        int cValue = cField.getInt(null);
        assertEquals("Variable 'c' should be 163", 163, cValue);

        Field bField = generatedClass.getField("b");
        boolean bValue = bField.getBoolean(null);
        assertEquals("Variable 'b' should be true", true, bValue);

        Field fField = generatedClass.getField("f");
        float fValue = fField.getFloat(null);
        assertEquals("Variable 'f' should be 0.19f", 0.19f, fValue, 0.0001f); // Use a delta for floats

        Field sField = generatedClass.getField("s");
        String sValue = (String) sField.get(null);
        assertEquals("Variable 's' should be \"hello\"", "\"hello\"", sValue);
    }

    @Test
    public void testShadowScoping() throws Exception {

        Class<?> generatedClass = compileAndLoad("shadowScoping.txt");

        Constructor<?> constructor = generatedClass.getDeclaredConstructor();

        Field eField = generatedClass.getField("e");
        int eValue = eField.getInt(null);
        assertEquals("Global Variable 'e' should be 81", 81, eValue);

        Object instance = constructor.newInstance();
        Method mainMethod = generatedClass.getMethod("test");
        int result = (int) mainMethod.invoke(instance);

        assertEquals("Local Variable 'e' should return 1", 1, result);
    }


}