package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.Arrays;


public class Lexer {
    private Reader input;
    private int current_position;
    private String buffer = "";

    public Lexer(Reader input) {
        this.input = input;
        advance();
    }

    public Symbol getNextSymbol() {
        return null;
    }


    private void advance() {
        try {
            current_position= input.read();
        }
        catch (IOException e) {
            current_position = -1;

        }
    }

    private String group() {
        String attribute = buffer;
        buffer = "";
        return buffer;
    }

    private boolean match_number(){
        int state = 0;
        char current_char = (char) current_position;
        boolean still_natural_number = true;

        while (still_natural_number){
            if(state == 0) {
                if (current_char > '0' && current_char <= '9') {
                    state = 1;
                    buffer = buffer + current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    still_natural_number = false;
                }
            }
            else if (state == 1) {
                if (current_char >= '0' && current_char <= '9'){
                    buffer = buffer + current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    state = 2;
                }
            }
            else if (state == 2) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
       String test= "123 =";
       Reader test2 = new StringReader(test);
       Lexer lexer = new Lexer(test2);
       System.out.println(lexer.match_number());
    }
}
