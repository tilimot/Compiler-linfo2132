package compiler.Parser.Grammar;

import compiler.Parser.Parser;
import compiler.Semantic.Semantic;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
        this.funcParams = funcParams;
        this.closingParenthesis =closingParenthesis;
        this.return_type = return_type;
        this.block = block;
    }

    public FunctionStatement(String fun_, String identifier, String openParenthesis, String closingParenthesis, Block block, int tabIndex){
        //Function main
        super(tabIndex);
        this.fun_ = fun_;
        this.identifier = identifier;
        this.openParenthesis = openParenthesis;
        this.closingParenthesis =closingParenthesis;
        this.block = block;
        this.funcParams = new ArrayList<>();
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public ArrayList<FuncParam> getParams(){
        return this.funcParams;
    }

    public ArrayList<Type> getReturn_type(){
        return this.return_type;
    };
    public Block getBlock(){
        return this.block;
    };

    @Override
    public void semanticAnalysis(SymbolTable symbolTable) throws Exception {

        LinkedHashMap<String, Type> st = symbolTable.getTable();
        SymbolTable params = new SymbolTable(symbolTable, "params");
        symbolTable.setChildTable(params);
        for (FuncParam funcParam : funcParams) {
            params.getTable().put(funcParam.identifier, funcParam.type.getFirst());
            st.put(funcParam.identifier, funcParam.type.getFirst());
        }
        if (return_type != null) {
            st.put(identifier, return_type.getFirst());
            st.put("RETURN_TYPE", return_type.getFirst());
            symbolTable.getParentTable().getTable().put(identifier, return_type.getFirst());
        }
        block.semanticAnalysis(symbolTable);




    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        block.tabIndex = tabIndex+1;
        StringBuilder funcParamStr = new StringBuilder();
        if (funcParams != null) {
            for (FuncParam funcParam : funcParams) {
                funcParam.tabIndex = tabIndex+2;
                funcParamStr.append(funcParam);
            }
        }
        StringBuilder returnTypeStr = new StringBuilder();
        if (return_type != null) {
            for (Type type : return_type) {
                if (type instanceof SimpleType) {
                    ((SimpleType) type).tabIndex = tabIndex + 1;
                } else if (type instanceof ArrayDeclarationBracket) {
                    ((ArrayDeclarationBracket) type).tabIndex = tabIndex + 1;
                }
                returnTypeStr.append(type);
            }
        }
        return t + "FUNC : "+ "\n" + tNext + fun_ + "\n" + tNext + identifier + "\n" + tNext + openParenthesis + "\n"
                + tNext + "PARAM :" +"\n" + funcParamStr + "\n" + tNext + closingParenthesis + "\n" + returnTypeStr + "\n" + block;
    }


}
