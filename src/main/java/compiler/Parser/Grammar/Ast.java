package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Ast {
    //TODO: Miss functions
    ArrayList<Constant> constants;
    ArrayList<Record> records;
    ArrayList<AssignementStatement> globalVariables;

    public Ast(ArrayList<Constant> constants, ArrayList<Record> records, ArrayList<AssignementStatement> globalVariables) {
        this.constants= constants;
        this.records = records;
        this.globalVariables = globalVariables;
    }

}
