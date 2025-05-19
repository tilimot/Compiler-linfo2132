package compiler.Parser.Grammar;

import compiler.Lexer.TokenType;

import static java.lang.Character.isUpperCase;

public class SimpleType extends Type {
    String value;
    int tabIndex;


    public SimpleType(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    public TokenType getType(){
        return switch (value) {
            case "int" -> TokenType.INTEGER;
            case "of" -> TokenType.OF;
            case "array" -> TokenType.ARRAY;
            case "float" -> TokenType.FLOAT;
            case "string" -> TokenType.STRINGS;
            case "bool" -> TokenType.BOOLEAN;
            default -> {
                if (!value.isEmpty() && Character.isUpperCase(value.charAt(0))) {
                    yield TokenType.RECORD_NAME;
                } else {
                    yield TokenType.IDENTIFIER;
                }
            }
        };
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t +value;
    }
}
