package compiler.Parser.Grammar;

public class LeftSideRecordAccess extends LeftSide {
    public String identifier;
    String  recordAttributes;
    int tabIndex;

    public LeftSideRecordAccess(String identifier, String recordAttributes, int tabIndex){
        this.identifier=identifier;
        this.recordAttributes=recordAttributes;
        this.tabIndex=tabIndex;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return null; // ou autre selon ton design
    }


    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t + identifier + "\n"+t+recordAttributes.toString();
    }
}
