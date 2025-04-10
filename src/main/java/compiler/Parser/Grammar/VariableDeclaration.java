package compiler.Parser.Grammar;

import java.util.ArrayList;

public class VariableDeclaration extends Statement {
    String identifier;
    ArrayList<Type> type;
    String eol;


    public VariableDeclaration(String identifier, ArrayList<Type> type, String eol, int tabIndex){
        super(tabIndex);
        this.identifier = identifier;
        this.type = type;
        this.eol= eol;

    }

    public String toString() {
        //TODO Implem arraylist type print
        String t = "\t".repeat(tabIndex);
        //type.tabIndex = tabIndex;
        return t+identifier + "\n" + type.toString() + "\n" + t + eol + "\n";
    }
}
