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

    public String getType(){
        return leftBracket + rightBracket;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return "\n"+t+leftBracket + "\n"+t + rightBracket;
    }
}
