package compiler.Parser.Grammar;

public class Param {
    Type type;
    String identifier;

    public Param(Type type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return type.toString() + "\t" +identifier + "\n";
    }
}
