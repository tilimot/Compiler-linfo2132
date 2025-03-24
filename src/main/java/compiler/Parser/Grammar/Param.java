package compiler.Parser.Grammar;

public class Param {
    Type type;
    String identifier;
    int tabIndex;


    public Param(Type type, String identifier, int tabIndex) {
        this.type = type;
        this.identifier = identifier;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        return "\n".repeat(tabIndex) +type.toString() + "\t" +identifier + "\n";
    }
}
