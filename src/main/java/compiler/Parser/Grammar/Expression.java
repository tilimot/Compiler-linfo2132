package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Expression {
    String value;

    public Expression(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "\n";
    }
}
