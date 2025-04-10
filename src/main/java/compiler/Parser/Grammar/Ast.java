package compiler.Parser.Grammar;

import java.util.ArrayList;

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
      //  this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String t = "\t".repeat(tabIndex);
        String tNext = "\t".repeat(tabIndex+1);
        for (Constant constant : constants) {
           // constant.tabIndex = tabIndex+1;
            sb.append(constant.toString());
        }
        return t+"Root :"+ "\n" +sb + "\n";
    }

}
