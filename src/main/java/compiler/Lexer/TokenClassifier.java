package compiler.Lexer;

import java.util.Set;
import java.util.regex.Pattern;

public class TokenClassifier {

    private static final Set<String> KEYWORDS = Set.of(
            "free", "final", "rec", "fun", "for", "while", "if", "else", "return"
    );

    private static final Set<String> BOOLEAN_VALUES = Set.of("true", "false");

    private static final Set<String> OPERATORS = Set.of(
            "=", "+", "-", "*", "/", "%", "==", "!=", "<", ">", "<=", ">=", "&&", "||",
            "(", ")", "{", "}", "[", "]", ".", ";", ","
    );

    public static boolean isComment(String token) {
        return token.startsWith("$");
    }

    public static boolean isKeyword(String token) {
        return KEYWORDS.contains(token);
    }

    public static boolean isBoolean(String token) {
        return BOOLEAN_VALUES.contains(token);
    }

    public static boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    public static boolean isNaturalNumber(String token) {
        return token.matches("0|[1-9][0-9]*");
    }

    public static boolean isFloatNumber(String token) {
        return token.matches("0|[1-9][0-9]*\\.\\d+|\\.\\d+");
    }

    public static boolean isString(String token) {
        return token.matches("\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\"");
    }

    public static boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    // Détermine le type d’un token
    public static TokenType classifyToken(String token) {
        if (isComment(token)) return TokenType.COMMENT;
        if (isKeyword(token)) return TokenType.KEYWORD;
        if (isBoolean(token)) return TokenType.BOOLEAN;
        if (isNaturalNumber(token)) return TokenType.NATURAL_NUMBER;
        if (isFloatNumber(token)) return TokenType.FLOAT_NUMBER;
        if (isString(token)) return TokenType.STRINGS;
        if (isOperator(token)) return TokenType.OPERATOR;
        if (isIdentifier(token)) return TokenType.IDENTIFIER;
        return null; // Si aucun type ne correspond
    }
}