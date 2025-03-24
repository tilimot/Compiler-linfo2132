package compiler.Parser.Grammar;


public class Type {

    SimpleType simpleType;
    ArrayPart arrayPart;

    public Type(SimpleType simpleType, ArrayPart arrayPart ) {
        this.simpleType = simpleType;
        this.arrayPart = arrayPart;
    }

    @Override
    public String toString() {
        return simpleType.toString() + "\t" + arrayPart.toString() + "\t";
    }
}

