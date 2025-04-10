package compiler.Parser.Grammar;

import compiler.Parser.Parser;

import java.util.ArrayList;

public class FunctionStatement extends Statement {
    String fun_;
    String identifier;
    String openParenthesis;
    Parser.TypeSymbolPair type;
    ArrayList<Param> params;
    String closingParenthesis;
    Type return_type;
    Block block;


    public FunctionStatement(String fun_, String identifier, String openParenthesis, ArrayList<Param> params, String closingParenthesis, Type return_type, Block block, int tabIndex){
        super(tabIndex);
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        this.params = params;
        this.closingParenthesis =closingParenthesis;
        this.return_type = return_type;
        this.block = block;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        block.tabIndex = tabIndex+1;
        StringBuilder paramsStr = new StringBuilder();
        for (Param param : params) {
            param.tabIndex = tabIndex + 1;
            paramsStr.append(param);
        }
        return t + "FUNC : "+ "\n" + tNext + fun_ + "\n" + tNext + identifier + "\n" + tNext + openParenthesis + "\n" + tNext + "PARAM : "
        + "\n" + paramsStr + "\n" + tNext + closingParenthesis + "\n" + tNext + return_type + "\n" + block;
    }
}
