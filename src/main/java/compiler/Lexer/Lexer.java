package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
//import java.lang.reflect.Array;


public class Lexer {
    private Reader input;
    private int current_position;
    private String buffer = "";

    public Lexer(Reader input) {
        this.input = input;
    }

    public Symbol getNextSymbol() {
        Match match = new Match(this.input);
        return match.symbol_match();

    }

    public static void main(String[] args){
       String test= "$ Source is\n   source 123 123.45 ;" ;
       Reader test2 = new StringReader(test);
       Lexer lexer = new Lexer(test2);

       int i= 0;
       while(i < 6){
           System.out.println(lexer.getNextSymbol());
           i++;
       }
    }
}
