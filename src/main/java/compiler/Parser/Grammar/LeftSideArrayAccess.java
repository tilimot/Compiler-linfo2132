package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideArrayAccess extends LeftSide{
    public String identifier;
    String opening_bracket;
    String natural_number;
    String closing_bracket;
    ArrayList<RecordDeclaration> recordAttributes;
    int tabIndex;

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return null; // ou autre selon ton design
    }

    public LeftSideArrayAccess (String identifier, String opening_bracket, String natural_number, String closing_bracket, ArrayList<RecordDeclaration> recordAttributes){
        this.identifier= identifier;
        this.opening_bracket=opening_bracket;
        this.natural_number=natural_number;
        this.closing_bracket=closing_bracket;
        this.recordAttributes=recordAttributes;
    }
}
