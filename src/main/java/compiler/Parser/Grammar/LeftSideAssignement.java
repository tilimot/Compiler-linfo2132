package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideAssignement extends LeftSide{
    public String identifier;
    public ArrayList<Type> type;
    int tabIndex;

    public LeftSideAssignement(String identifier, ArrayList<Type> type){
        this.identifier = identifier;
        this.type = type;
    }

    public String toString() {
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();
        if (!type.isEmpty()){
            typeStr.append("\n");}
        for (Type type : type) {
            if (type instanceof SimpleType) {
                ((SimpleType) type).tabIndex = tabIndex;
            } else if (type instanceof ArrayDeclarationBracket) {
                ((ArrayDeclarationBracket) type).tabIndex = tabIndex;

            }
            typeStr.append(type);

        }
        return t + identifier + typeStr;
    }
}
