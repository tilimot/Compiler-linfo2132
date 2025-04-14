package compiler.Parser.Grammar;

import compiler.Parser.Parser;

import java.util.ArrayList;

public class FunctionStatement extends Statement {
    String fun_;
    String identifier;
    String openParenthesis;
    //ArrayList<Type> type;
    ArrayList<FuncParam> funcParams;
    String closingParenthesis;
    ArrayList<Type> return_type;
    Block block;


    public FunctionStatement(String fun_, String identifier, String openParenthesis, ArrayList<FuncParam> funcParams, String closingParenthesis, ArrayList<Type> return_type, Block block, int tabIndex){
        super(tabIndex);
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        //this.type = type;
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
        StringBuilder funcParamStr = new StringBuilder();
        for (FuncParam funcParam : funcParams) {
            funcParam.tabIndex = tabIndex+2;
            funcParamStr.append(funcParam);
        }
        StringBuilder returnTypeStr = new StringBuilder();
        for (Type type : return_type) {
            if (type instanceof SimpleType) {
                ((SimpleType) type).tabIndex = tabIndex + 1;
            } else if (type instanceof ArrayDeclarationBracket) {
                ((ArrayDeclarationBracket) type).tabIndex = tabIndex + 1;
            }
            returnTypeStr.append(type);
        }
        return t + "FUNC : "+ "\n" + tNext + fun_ + "\n" + tNext + identifier + "\n" + tNext + openParenthesis + "\n"
                + tNext + "PARAM :" +"\n" + funcParamStr + "\n" + tNext + closingParenthesis + "\n" + returnTypeStr + "\n" + block;
    }

    @Override
    public void semanticAnalysis() throws Exception {

    }
}
