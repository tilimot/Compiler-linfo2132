package compiler.Parser.Grammar;

import java.lang.annotation.ElementType;
import java.util.ArrayList;

public class FunctionStatement extends Statement {
    String fun_;
    String identifier;
    String openParenthesis;
    Type type;
    ArrayList<Param> params;
    String closingParenthesis;
    Type return_type;
    Block block;

    public FunctionStatement(String fun_, String identifier, String openParenthesis, Type type, ArrayList<Param> params, String closingParenthesis, Type return_type, Block block ){
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        this.type = type;
        this.params = params;
        this.closingParenthesis =closingParenthesis;
        this.return_type = return_type;
        this.block = block;
    }

}
