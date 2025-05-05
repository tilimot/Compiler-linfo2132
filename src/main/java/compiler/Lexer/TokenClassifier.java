package compiler.Lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

public class TokenClassifier {

    public static int advance(Reader input){
        int current_position;
        try {
            return input.read();
        }
        catch ( IOException e) {
            return -1;
        }
    }
    private static final Set<String> KEYWORDS = Set.of(
            "free", "final", "rec", "fun", "for", "while", "if", "else", "return"
    );

    private static final Set<String> BOOLEAN_VALUES = Set.of("true", "false");

    private static final Set<String> OPERATORS = Set.of(
            "=", "+", "-", "*", "/", "%", "==", "!=", "<", ">", "<=", ">=", "&&", "||",
            "(", ")", "{", "}", "[", "]", ".", ","
    );
    private static final Set<String> BASE_TYPE = Set.of(
            "int","float","bool","string"
    );

    public static boolean isVoidType(String token) { return token.equals("void");}

    public static boolean isComment(String token) {
        return token.startsWith("$");
    }

    public static boolean isKeyword(String token) {
        return KEYWORDS.contains(token);
    }

    public static boolean isBaseType(String token){
        return BASE_TYPE.contains(token);
    }

    public static boolean isBoolean(String token) {
        return BOOLEAN_VALUES.contains(token);
    }

    public static boolean isIdentifier(String token) {
        Reader input = new StringReader(token);
        int token_length = token.length();
        int current_position = advance(input);
        char current_char = (char) current_position;
        int state = 0;

        for(int i = 0; i<token_length;i++){
            if(state ==0) {
                if ((Character.isLetter(current_char) && current_char != '@'  )|| current_char == '_') {
                    current_position = advance(input);
                    state =1;
                } else {
                    return false;
                }
            }
            else{
                if (Character.isLetterOrDigit(current_char) || current_char == '_') {
                    current_position = advance(input);
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isRecordAttribute(String token) {
        Reader input = new StringReader(token);
        int token_length = token.length();
        int current_position = advance(input);
        char current_char = (char) current_position;
        int state = 0;

        for(int i = 0; i<token_length;i++){
            if(state ==0) {
                if ((current_char =='.')) {
                    current_position = advance(input);
                    current_char = (char) current_position;
                    state =1;
                } else {
                    return false;
                }
            }
            else{
                if (Character.isLetterOrDigit(current_char) || current_char == '_' ) {
                    current_position = advance(input);
                    current_char = (char) current_position;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isMain(String token) {
        return token.equals("main");
    }

    public static boolean isRecordName(String token) {
        Reader input = new StringReader(token);
        int token_length = token.length();
        int current_position = advance(input);
        char current_char = (char) current_position;
        int state = 0;

        for(int i = 0; i<token_length;i++){
            if(state ==0) {
                if (Character.isUpperCase(current_char)) {
                    current_position = advance(input);
                    current_char = (char) current_position;
                    state =1;
                } else {
                    return false;
                }
            }
            else{
                if (Character.isLetterOrDigit(current_char) || current_char == '_') {
                    current_position = advance(input);
                    current_char = (char) current_position;
                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNaturalNumber(String token) {
        Reader input = new StringReader(token);
        int token_length = token.length();
        int current_position = advance(input);
        char current_char = (char) current_position;

        for(int i = 0; i<token_length;i++){
            if(Character.isDigit(current_char)){
                current_position = advance(input);
                current_char = (char) current_position;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public static boolean isFloatNumber(String token) {
        Reader input = new StringReader(token);
        int token_length = token.length();
        if(token_length<=1){
            return false;
        }
        int current_position = advance(input);
        char current_char = (char) current_position;

        for(int i = 0; i<token_length;i++){
            if(Character.isDigit(current_char) || current_char == '.'){
                current_position = advance(input);
                current_char = (char) current_position;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public static boolean isString(String token) {
        return (token.startsWith("\"") && token.endsWith("\"") )|| (token.startsWith("'") && token.endsWith("'")) ;
    }

    public static boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    public static boolean isEOL(String token) {
        return token.equals(";");
    }

    public static boolean isEOF(String token) {
        return token.equals("@eof");
    }

    // Détermine le type d’un token
    public static TokenType classifyToken(String token, String tokenNext) {
        if (isEOF(token)) return TokenType.EOF;

        if (isComment(token)) return TokenType.COMMENT;
        if (isMain(token))return TokenType.MAIN;
        if (isKeyword(token)) return TokenType.KEYWORD;
        if (isBoolean(token)) return TokenType.BOOLEAN;
        if(isBaseType(token)) return TokenType.BASE_TYPE;
        if(isRecordName(token)) return TokenType.RECORD_NAME;
        if(isVoidType(token)) return TokenType.VOID_TYPE;
        if (isIdentifier(token)) {
            if (tokenNext.equals("("))return TokenType.FUNC_NAME;
            else return TokenType.IDENTIFIER;}
        if (isNaturalNumber(token)) return TokenType.INTEGER;
        if (isFloatNumber(token)) return TokenType.FLOAT;
        if (isString(token)) return TokenType.STRINGS;
        if (isEOL(token)) return TokenType.EOL;
        if (isOperator(token)) return TokenType.OPERATOR;
        if (isRecordAttribute(token)) return TokenType.ATTRIBUTE;


        return null; // Si aucun type ne correspond
    }
}