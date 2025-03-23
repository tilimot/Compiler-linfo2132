package compiler.Parser.Grammar;

import java.util.ArrayList;

public class MethodCall {
    String identifier;
    String opening_parenthesis;
    ArrayList<Param> parameters ;
    String closing_parenthesis;

    public MethodCall(String identifier, String opening_parenthesis, ArrayList<Param> parameters, String closing_parenthesis){
        this.identifier = identifier;
        this.opening_parenthesis = opening_parenthesis;
        this.parameters = parameters;
        this.closing_parenthesis = closing_parenthesis;
    }
}
