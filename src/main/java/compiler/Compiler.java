/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package compiler;

import compiler.Lexer.FileToReader;
import compiler.Lexer.Lexer;
import compiler.Parser.Grammar.*;
import compiler.Parser.Parser;
import compiler.Parser.Semantic;

import java.io.Reader;

public class Compiler {
    public static void main(String[] args) throws Exception {
       if (args.length == 2 && args[0].equals("-lexer")) {

           String filepath = args[1];
           Reader readerFile = FileToReader.getReaderFromFile(filepath);
           Lexer lexer = new Lexer(readerFile);

           while (lexer.hasNextSymbol()) {
               System.out.println(lexer.getNextSymbol());
           }
       }

       if (args.length == 2 && args[0].equals("-parser")){
            String filepath = args[1];
            Reader readerFile = FileToReader.getReaderFromFile(filepath);
            Lexer lexer = new Lexer(readerFile);
            Parser parser = new Parser(lexer);
            Ast myAst = parser.getAST();
            System.out.println(myAst);
            Semantic semantic = new Semantic(myAst);
            semantic.startAnalysis();

       }
    }
}
