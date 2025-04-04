package compiler.Parser.Grammar;

public class ArrayAccesBracket {
    String leftBracket;
    String integerValue;
    String rightBracket;

    public ArrayAccesBracket(String leftBracket, String integerValue, String rightBracket){
        this.leftBracket = leftBracket;
        this.integerValue = integerValue;
        this.rightBracket = rightBracket;
    }
}
