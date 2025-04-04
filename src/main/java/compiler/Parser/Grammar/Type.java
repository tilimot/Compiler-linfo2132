package compiler.Parser.Grammar;


public class Type {

    SimpleType simpleType;
    boolean isArray;
    int tabIndex;


    public Type(SimpleType simpleType, boolean isArray, int tabIndex) {
        this.simpleType = simpleType;
        this.isArray=isArray;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t+simpleType.toString()+ (isArray ? "[]" : "");
    }
}

