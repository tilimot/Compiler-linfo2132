package compiler.Parser.Grammar;

public class VariableDeclaration extends Statement {
    String identifier;
    Type type;
    String eol;


    public VariableDeclaration(String identifier, Type type, String eol, int tabIndex){
        super(tabIndex);
        this.identifier = identifier;
        this.type = type;
        this.eol= eol;

    }

    public String toString() {
        return "\n".repeat(tabIndex) +identifier + "\n" + type.toString() + "\t" + eol + "\n";
    }
}
