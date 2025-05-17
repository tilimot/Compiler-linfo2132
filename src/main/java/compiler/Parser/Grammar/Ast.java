package compiler.Parser.Grammar;

import compiler.Semantic.Semantic;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class Ast {
    //TODO: Miss functions
    ArrayList<Constant> constants;
    ArrayList<Record> records;
    ArrayList<Statement> globalVariables;
    ArrayList<FunctionStatement> functions;
    int tabIndex;

    public Ast(ArrayList<Constant> constants, ArrayList<Record> records, ArrayList<Statement> globalVariables, ArrayList<FunctionStatement> functions, int tabIndex) {
        this.constants= constants;
        this.records = records;
        this.globalVariables = globalVariables;
        this.functions = functions;
        this.tabIndex = tabIndex;
    }

    public void semanticAnalysis(SymbolTable symbolTable) throws Exception {
        for (Constant constant : constants) {
            constant.semanticAnalysis(symbolTable);
        }
        for (Record record : records) {
            SymbolTable recSymbTable = new SymbolTable(symbolTable, record.recordsName);
            symbolTable.setChildTable(recSymbTable);
            record.semanticAnalysis(recSymbTable);
        }
        for (Statement globalVariable : globalVariables) {
            globalVariable.semanticAnalysis(symbolTable);
        }
        for (FunctionStatement function : functions) {
            SymbolTable funcSymbTable = new SymbolTable(symbolTable,function.identifier);
            symbolTable.setChildTable(funcSymbTable);
            function.semanticAnalysis(funcSymbTable);
        }
        //Todo function, record
    }


    public ArrayList<Constant> getConstant(){
        return this.constants;
    }

    public ArrayList<Record> getRecords(){
        return this.records;
    }

    public ArrayList<Statement> getGlobalVariables(){
        return this.globalVariables;
    }

    public ArrayList<FunctionStatement> getFunctions(){
        return this.functions;
    };



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String t = "\t".repeat(tabIndex);
        for (Constant constant : constants) {
            constant.tabIndex = tabIndex+1;
            sb.append(constant.toString());
        }
        for (Record record : records) {
            record.tabIndex = tabIndex+1;
            sb.append(record.toString());
        }
        for (Statement globalVariable : globalVariables) {
            globalVariable.tabIndex = tabIndex+1;
            sb.append(globalVariable.toString());
        }
        for (FunctionStatement function : functions) {
            function.tabIndex = tabIndex+1;
            sb.append(function.toString());
        }
        return t+"Root :"+ "\n" +sb + "\n";
    }

}
