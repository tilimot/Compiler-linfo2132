package compiler.Parser.Grammar;

import java.util.ArrayList;

public class MethodCall extends Statement {
    String identifier;
    String opening_parenthesis;
    ArrayList<Param> parameters ;
    String closing_parenthesis;
    String eol;

    public MethodCall(String identifier, String opening_parenthesis, ArrayList<Param> parameters, String closing_parenthesis, String eol){
        this.identifier = identifier;
        this.opening_parenthesis = opening_parenthesis;
        this.parameters = parameters;
        this.closing_parenthesis = closing_parenthesis;
        this.eol= eol;
    }
}
