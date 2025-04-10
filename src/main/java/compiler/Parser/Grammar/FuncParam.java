package compiler.Parser.Grammar;

import java.util.ArrayList;

public class FuncParam {
    ArrayList<Type> type;
    String identifier;
    int tabIndex;


    public FuncParam(ArrayList<Type> type, String identifier, int tabIndex) {
        this.type = type;
        this.identifier = identifier;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        //TODO implem array list type
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        //type.tabIndex = tabIndex+1;
        return t + "PARAM" + "\n" + type.toString() + "\n"+ tNext+ identifier + "\n";
    }
}
