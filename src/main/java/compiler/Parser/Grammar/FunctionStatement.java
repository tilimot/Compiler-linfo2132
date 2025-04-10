package compiler.Parser.Grammar;

import compiler.Parser.Parser;

import java.util.ArrayList;

public class FunctionStatement extends Statement {
    String fun_;
    String identifier;
    String openParenthesis;
    ArrayList<Type> type;
    ArrayList<FuncParam> funcParams;
    String closingParenthesis;
    ArrayList<Type> return_type;
    Block block;


    public FunctionStatement(String fun_, String identifier, String openParenthesis, ArrayList<Type> type, ArrayList<FuncParam> funcParams, String closingParenthesis, ArrayList<Type> return_type, Block block, int tabIndex){
        super(tabIndex);
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        this.type = type;
        this.funcParams = funcParams;
        this.closingParenthesis =closingParenthesis;
        this.return_type = return_type;
        this.block = block;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        block.tabIndex = tabIndex+1;
        return t + "FUNC : "+ "\n" + tNext + fun_ + "\n" + tNext + identifier + "\n" + tNext + openParenthesis + "\n" + tNext + type.toString() + "\n" + tNext + funcParams.toString() + "\n" + tNext + closingParenthesis + "\n" + tNext + return_type.toString() + "\n" + block.toString();
    }
}
