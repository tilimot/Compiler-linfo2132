package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;


public class Match {
    private Reader input;
    private int current_position;
    private String buffer = "";

    public Match(Reader input) {
        this.input = input;
        advance();
    }

    private void advance() {
        try {
            current_position = input.read();
        }
        catch (IOException e) {
            current_position = -1;
        }
    }

    private Symbol group(TokenType token) {
        String attribute = buffer;
        buffer = "";
        Symbol symbol = new Symbol(token, attribute);
        return symbol;
    }

    public Symbol symbol_match() {
        TokenType token = null;

        // search for a match
        if(match_ws()){
            token = TokenType.WS;
        }
        else if (match_identifier()) {
            token = TokenType.IDENTIFIER;
        }
        else if (match_natural()){
            if (match_float()){
                token = TokenType.FLOAT_NUMBER;
            }
            else {
                token = TokenType.NATURAL_NUMBER;
            }
        }
        else if (match_string()) {
            token = TokenType.STRING;
        }
        else if (match_comment()) {
            token = TokenType.COMMENT;
        }
        else if(match_eol()){
            token = TokenType.EOL;
        }

        // return match if found, else return null
        if (token != null){
            return group(token);
        }
        return  null;
    }

    private boolean match_pattern(char begin_pattern, char end_pattern){
        int state = 0;
        char current_char = (char) this.current_position;
        boolean still_pattern = true;

        while (still_pattern){
            if(state == 0) {
                if (current_char == begin_pattern) {
                    state = 1;
                    this.buffer += current_char;
                    advance();
                    current_char = (char) this.current_position;
                }
                else{
                    still_pattern = false;
                }
            }
            else if (state == 1) {
                if (current_char != end_pattern){
                    this.buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    state = 2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match_array(String[] pattern_array){

        char current_char = (char) current_position;
        buffer += current_char;
        int buff_length = buffer.length();
        for (String element:pattern_array){
            int state=0;
            int i = 0;
            boolean still_element = true;
            int element_length = element.length();
            while(still_element){
                if (state==0){
                   if(buff_length <= element_length && element.substring(0, buff_length-1).equals(buffer)){
                       System.out.println("here 1");
                       state= 1;
                       advance();
                       current_char = (char) current_position;

                   }
                   else{
                       still_element=false;
                   }
                }
                else if (state ==1){
                    System.out.println(element.charAt(buff_length-1));
                    System.out.println(current_char);

                    if(current_position == -1 && buff_length == element_length){
                        return true;
                    }
                    else if((buff_length <= element_length) && (element.charAt(buff_length) == current_char)){
                        System.out.println("here 2");
                        buffer += current_char;
                        buff_length += 1;
                        advance();
                        current_char = (char) current_position;
                    }
                    else{
                        state=2;
                        still_element = false;
                    }
                }
            }

        }
        return false;
    }

    private boolean match_natural(){
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
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match_float(){
        int state = 0;
        char current_char = (char) current_position;
        boolean still_float = true;
        while (still_float){
            if (state == 0) {
                if (current_char == '.') {
                    state = 1;
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    still_float = false;
                }
            }
            else if (state == 1) {
                if (current_char >= '0' && current_char <= '9'){
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    state = 2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match_ws(){
        int state = 0;
        char current_char = (char) current_position;
        boolean still_ws = true;
        while (still_ws){
            if (state == 0) {
                if (current_char == ' '|| current_char == '\t' || current_char == '\n') {
                    state = 1;
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    still_ws = false;
                }
            }
            else if (state == 1) {
                if (current_char == ' '|| current_char == '\t' || current_char == '\n'){
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    state = 2;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean match_eol(){
        char current_char = (char) current_position;
        return current_char == ';';
    }


    private boolean match_comment(){
        return match_pattern('$','\n');
    }

    private boolean match_string(){
        return match_pattern('"','"');
    }

    private boolean match_identifier(){

        int state = 0;
        char current_char = (char) current_position;
        boolean still_identifier = true;

        while (still_identifier){
            if (state ==0){
                if (current_char == '_'|| (current_char>='a' && current_char<='z') ||(current_char>='A' && current_char<='Z')){
                    state = 1;
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else {
                    still_identifier = false;
                }
            }
            else if (state == 1){
                if (current_char == '_'|| (current_char>='a' && current_char<='z') ||(current_char>='A' && current_char<='Z')||(current_char>='0' && current_char <='9')){
                    buffer += current_char;
                    advance();
                    current_char = (char)  current_position;
                }
                else{
                    state =2;
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String[] args){
        String s = "for";
        Reader r = new StringReader(s);
        Match match = new Match(r);
        String[] arr = {"if","else","for","foreach"};
        System.out.println(match.match_array(arr));
    }

}
