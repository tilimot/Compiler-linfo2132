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
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        StringBuilder paramStr = new StringBuilder();

        for (Param param : parameters) {
            param.tabIndex = tabIndex+2;
            paramStr.append(param);
        }
        return t+"METD_CALL : " + "\n" + tNext + identifier + "\n" + tNext + opening_parenthesis + "\n" + tNext + "PARAM : " + "\n" + paramStr + "\n" + tNext + closing_parenthesis + "\n" + tNext + eol + "\n";
    }
}
