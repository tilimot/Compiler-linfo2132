package compiler.Exception;

import compiler.Parser.Grammar.Type;

public class VariableException extends Exception {
    public VariableException(Type type1, Type type2) {
        super("Trying to assign one variable to another of differents types : "+type1+" and  "+type2);
    }
}
