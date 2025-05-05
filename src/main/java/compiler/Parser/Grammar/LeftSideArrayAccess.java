package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideArrayAccess extends LeftSide{
    public String identifier;
    String opening_bracket;
    String natural_number;
    String closing_bracket;
    ArrayList<String> recordAttributes;
    int tabIndex;

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return null; // ou autre selon ton design
    }

    public LeftSideArrayAccess (String identifier, String opening_bracket, String natural_number, String closing_bracket, ArrayList<String> recordAttributes){
        this.identifier= identifier;
        this.opening_bracket=opening_bracket;
        this.natural_number=natural_number;
        this.closing_bracket=closing_bracket;
        this.recordAttributes=recordAttributes;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();

        if (!recordAttributes.isEmpty()){
            typeStr.append("\n").append(t);}
        for (String recordAttribute : recordAttributes) {
            typeStr.append(recordAttribute);
        }
        return t + identifier + opening_bracket + natural_number + closing_bracket  + t + typeStr;
    }
}
