package compiler.Parser.Grammar;

public class SimpleType {
    String value;
    int tabIndex;


    public SimpleType(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t +value + "\n";
    }
}
