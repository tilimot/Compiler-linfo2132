package compiler.Parser.Grammar;


public class Type {

    SimpleType simpleType;
    ArrayPart arrayPart;
    int tabIndex;


    public Type(SimpleType simpleType, ArrayPart arrayPart, int tabIndex) {
        this.simpleType = simpleType;
        this.arrayPart = arrayPart;
        this.tabIndex = tabIndex;

    }

    @Override
    public String  toString() {
        String t = "\t".repeat(tabIndex);
        arrayPart.tabIndex = tabIndex;
        simpleType.tabIndex = tabIndex;

        return simpleType.toString()+ "\n" +  arrayPart.toString();
    }
}

