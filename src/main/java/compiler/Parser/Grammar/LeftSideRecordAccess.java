package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideRecordAccess extends LeftSide {
    String identifier;
    RecordAttribute recordAttributes;
    int tabIndex;

    public LeftSideRecordAccess(String identifier, RecordAttribute recordAttributes){
        this.identifier=identifier;
        this.recordAttributes=recordAttributes;
    }


    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();

        return t + identifier + "\n" + t + typeStr;
    }
}
