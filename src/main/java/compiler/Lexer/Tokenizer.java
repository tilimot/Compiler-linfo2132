package compiler.Lexer;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> splitIntoTokens(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean inString = false;

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
                inString = true;
                buffer.append(c);
            } else if (Character.isWhitespace(c)) {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
            } else if (isSymbol(c)) {
                if (buffer.length() > 0) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                }
                result.add(Character.toString(c));
            } else {
                buffer.append(c);
            }
        }
        if (buffer.length() > 0) result.add(buffer.toString());
        return result;
    }

    private boolean isSymbol(char c) {
        return "=+-*/%(){}[].,;<>!&|".indexOf(c) != -1;
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