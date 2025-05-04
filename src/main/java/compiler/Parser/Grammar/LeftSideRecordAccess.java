package compiler.Parser.Grammar;

public class LeftSideRecordAccess extends LeftSide {
    public String identifier;
    RecordDeclaration recordAttributes;
    int tabIndex;

    public LeftSideRecordAccess(String identifier, RecordDeclaration recordAttributes){
        this.identifier=identifier;
        this.recordAttributes=recordAttributes;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return null; // ou autre selon ton design
    }


    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();

        return t + identifier + "\n" + t + typeStr;
    }
}
