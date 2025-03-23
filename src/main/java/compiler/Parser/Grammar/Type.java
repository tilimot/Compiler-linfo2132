package compiler.Parser.Grammar;


public class Type {

    SimpleType simpleType;
    ArrayPart arrayPart;

    public Type(SimpleType simpleType, ArrayPart arrayPart ) {
        this.simpleType = simpleType;
        this.arrayPart = arrayPart;
    }

}

