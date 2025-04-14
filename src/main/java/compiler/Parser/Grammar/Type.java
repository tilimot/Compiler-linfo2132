package compiler.Parser.Grammar;


import compiler.Lexer.TokenType;

public abstract class Type {
    public abstract TokenType getType();
    public abstract String getValue();
}

