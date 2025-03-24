package compiler.Parser.Grammar;

public class SimpleType {
    String value;

    public SimpleType(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "\n";
    }
}
