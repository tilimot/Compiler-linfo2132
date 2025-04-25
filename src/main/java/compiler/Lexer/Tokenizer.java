package compiler.Lexer;

import compiler.Exception.FloatException;

import java.io.*;
import java.util.*;

public class Tokenizer {
    private BufferedReader reader;
    private List<String> tokens;
    private int currentIndex = 0;

    public Tokenizer(Reader input) {
        this.reader = new BufferedReader(input);
        this.tokens = new ArrayList<>();
        tokenize();

    }

    public class TokenResult {
        /*
         * Class used only for splitting line into Float or Int
         */
        public StringBuilder token;
        public  int position;

        public TokenResult(StringBuilder token, int position) {
            this.token = token;
            this.position = position;
        }
        public StringBuilder getToken(){ return this.token;}

        public int getPosition() {
            return position;
        }
    }


    private void tokenize() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                int commentIndex = line.indexOf('$');

                if (commentIndex != -1) {
                    // Pass the comments
                    //String comment = line.substring(commentIndex);
                    //tokens.add(comment.trim());

                    line = line.substring(0, commentIndex);
                }

                tokens.addAll(splitIntoTokens(line));
            }
            tokens.add("@eof");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> splitIntoTokens(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean inString = false;
        boolean inWord = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inString) {
                buffer.append(c);
                if (c == '"' && buffer.length() > 1) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                    inString = false;
                }
            } else if (c == '"') {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
                inString = true;
                buffer.append(c);
            } else if (Character.isWhitespace(c)) {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
                inWord = false;
            } else if (isSymbol(c) && c != '.') {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
                result.add(Character.toString(c));
            } else if (c == '.' && i > 0 && i < line.length() - 1 &&
                    Character.isLetterOrDigit(line.charAt(i - 1)) &&
                    Character.isLetterOrDigit(line.charAt(i + 1))) {
                // Cas spécial item.value → item, .value
                result.add(buffer.toString()); // Ajoute 'item'
                buffer.setLength(0);           // Reset le buffer
                buffer.append('.');            // Commence le prochain token par '.'
                inWord = true;
            } else if ((Character.isDigit(c) || (c == '.')) && !inWord) {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
                TokenResult token = splitIntoFloatOrIntToken(line, i);
                i = token.position - 1;
                result.add(token.token.toString());
            } else {
                inWord = true;
                buffer.append(c);
            }
        }

        return result;
    }

    public TokenResult splitIntoFloatOrIntToken(String input, int i){
        /*
         * Take the string, the position in the string. Use an automaton to extract float or integer token.
         *
         * */
        StringBuilder buffer = new StringBuilder();
        int l = input.length();
        int state = 0;

        while(i < l) {
            char c = input.charAt(i);

            if(state == 0 && c=='0'){
                // Manage 0000.123.. , 00023...
                state = 1;
                buffer.append(c);
            }
            else if (state==0 && c==('.')) {
                // Manage float written as .3456

                state = 4;
                buffer.append('0');
                buffer.append(c);

            } else if (state==0){
                // Go to integer State
                state = 2;
                buffer.append(c);
            }
            else if (state == 1) {
                if (c=='0'){
                    // Series of 0 aren't counted
                    i+=1;
                    continue;
                } else if (Character.isDigit(c)){
                    // Go to integer State
                    buffer.setLength(0);
                    buffer.append(c);
                    state=2;
                } else if (c=='.') {
                    // Go to float State
                    buffer.append(c);
                    state=4;
                }
                else {
                    // Go to terminal state (not a Float or an Integer Anymore)
                    return new TokenResult(buffer, i);
                }
            }
            else if (state==2) {
                // Integer State
                if (Character.isDigit(c)){
                    buffer.append(c);
                }
                else if (c=='.'){
                    // Go to Float State
                    buffer.append(c);
                    state=4;
                }
                else{
                    // Go to terminal state (not a Float or an Integer Anymore)
                    return new TokenResult(buffer, i);
                }
            }
            else if (state==3) {
                // Terminal State. Return Buffer
                return new TokenResult(buffer, i);
            }

            else {
                // Float State
                if (Character.isDigit(c)){
                    buffer.append(c);
                } else if (Character.isLetter(c)) {
                    throw new FloatException("Integer doesn't have attributes");
                } else{
                    return new TokenResult(buffer, i);
                }
            }
            i+=1;
        }
        return new TokenResult(buffer, i);
    }


    private boolean isSymbol(char c) {
        return "=+-*/%(){}[],;<>!&|".indexOf(c) != -1;
    }

    public String nextToken() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex++);
        }
        return null;
    }

    public  List<String> getTokens() {
        return tokens;
    }




}