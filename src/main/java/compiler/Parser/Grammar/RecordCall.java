package compiler.Parser.Grammar;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordCall extends Statement {
    int tabIndex;
    String equal;
    String identifier1;
    String identifier2;
    ArrayList<Param> params;


    public RecordCall(LeftSide leftSide, int tabIndex, ArrayList<Param> params, String equal, String identifier2) {
        super(tabIndex);
        this.tabIndex = tabIndex;
        this.params = params;
        this.equal = equal;
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;

    }

    @Override
    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex + 1);
        StringBuilder paramStr = new StringBuilder();

        for (Param param : params) {
            param.tabIndex = tabIndex + 2;
            paramStr.append(param);
        }
        return t + "RECORD_CALL : " + "\n" + tNext + equal + "\n" + tNext + identifier1 + "\n" + tNext + "PARAM : " + "\n" + paramStr;
    }


}
