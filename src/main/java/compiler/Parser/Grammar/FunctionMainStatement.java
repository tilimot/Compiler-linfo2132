package compiler.Parser.Grammar;

import java.util.ArrayList;

public class FunctionMainStatement extends FunctionStatement {
    String fun_;
    String identifier;
    String openParenthesis;
    String closingParenthesis;
    Block block;


    public FunctionMainStatement(String fun_, String identifier, String openParenthesis, String closingParenthesis, Block block, int tabIndex) {
        super(fun_,identifier,openParenthesis,closingParenthesis,block,tabIndex);
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        this.closingParenthesis =closingParenthesis;
        this.block = block;
    }
}
