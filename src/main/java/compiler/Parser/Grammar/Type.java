package compiler.Parser.Grammar;

public class Type {
    public String value;
    int tabIndex;


    public Type(String value, int tabIndex){
        this.value = value;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        return t +value;
    }
}
