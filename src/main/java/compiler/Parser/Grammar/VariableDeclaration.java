package compiler.Parser.Grammar;

import java.util.ArrayList;

public class VariableDeclaration extends Statement {
    String identifier;
    Type type;
    String eol;

    public VariableDeclaration(String identifier, Type type, String eol){
        this.identifier = identifier;
        this.type = type;
        this.eol= eol;
    }
}
