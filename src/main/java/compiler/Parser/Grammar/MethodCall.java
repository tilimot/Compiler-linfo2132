package compiler.Parser.Grammar;

import java.util.ArrayList;

public class MethodCall extends Statement {
    String identifier;
    String opening_parenthesis;
    ArrayList<Param> parameters ;
    String closing_parenthesis;
    String eol;


    public MethodCall(String identifier, String opening_parenthesis, ArrayList<Param> parameters, String closing_parenthesis, String eol, int tabIndex){
        super(tabIndex);
        this.identifier = identifier;
        this.opening_parenthesis = opening_parenthesis;
        this.parameters = parameters;
        this.closing_parenthesis = closing_parenthesis;
        this.eol= eol;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Param param : parameters) {
            sb.append(param.toString()).append("\t");
        }
        return "\n".repeat(tabIndex) +identifier + "\n" + opening_parenthesis + "\n" + sb.toString() + "\n" + closing_parenthesis + "\n" + eol + "\n";
    }
}
