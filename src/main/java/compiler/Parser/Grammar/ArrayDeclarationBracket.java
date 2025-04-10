package compiler.Parser.Grammar;

public class ArrayDeclarationBracket extends Type {
    String leftBracket;
    String rightBracket;
    int tabIndex;

    public ArrayDeclarationBracket(String leftbracket, String rightBracket, int tabIndex){
        this.leftBracket = leftbracket;
        this.rightBracket = rightBracket;
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t+leftBracket + "\n"+t + rightBracket +"\n";
    }
}
