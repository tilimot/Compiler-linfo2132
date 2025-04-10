package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideRecordAccess extends LeftSide {
    String identifier;
    RecordAttribute recordAttributes;

    public LeftSideRecordAccess(String identifier, RecordAttribute recordAttributes){
        this.identifier=identifier;
        this.recordAttributes=recordAttributes;
    }
}
