package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Record {
    String recordsName;
    String rec_;
    String openingBracket;
    ArrayList<VariableDeclaration> declaration;
    String closingBracket;

    public Record(String recordsName, String rec_, String openingBracket, ArrayList<VariableDeclaration> declaration, String closingBracket ){
        this.recordsName = recordsName;
        this.rec_ = rec_;
        this.openingBracket = openingBracket;
        this.declaration = declaration;
        this.closingBracket = closingBracket;
    }

}
