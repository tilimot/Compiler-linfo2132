package compiler.Parser.Grammar;


public class Type {

    SimpleType simpleType;
    int tabIndex;


    public Type(SimpleType simpleType, int tabIndex) {
        this.simpleType = simpleType;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t+simpleType.toString();
    }
}

