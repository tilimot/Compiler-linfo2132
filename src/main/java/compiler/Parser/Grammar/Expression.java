package compiler.Parser.Grammar;

public class Expression {
    String value;
    int tabIndex;



    public Expression(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        return tNext+value + "\n";
    }
}
