package compiler.Parser.Grammar;

public class ArrayPart {
    String leftBracket;
    String rigthBracket;
    public ArrayPart(String leftbracket,String rigthBracket){
        this.leftBracket = leftbracket;
        this.rigthBracket = rigthBracket;
    }

    @Override
    public String toString() {
        return leftBracket + "\n" + rigthBracket + "\n";
    }
}
