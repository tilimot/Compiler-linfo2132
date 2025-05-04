package compiler.Parser.Grammar;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordCall extends Statement {
    int tabIndex;
    String equal;
    String identifier;
    ArrayList<Param> params;


    public RecordCall(int tabIndex, ArrayList<Param> params, String equal, String identifier) {
        super(tabIndex);
        this.tabIndex = tabIndex;
        this.params = params;
        this.equal = equal;
        this.identifier = identifier;

    }

    @Override
    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {

    }
}
