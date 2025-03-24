package compiler.Parser.Grammar;

public class ArrayPart {
    String leftBracket;
    String rigthBracket;
    int tabIndex;

    public ArrayPart(String leftbracket, String rigthBracket, int tabIndex){
        this.leftBracket = leftbracket;
        this.rigthBracket = rigthBracket;
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t+leftBracket + "\n"+t + rigthBracket+"\n";
    }
}
