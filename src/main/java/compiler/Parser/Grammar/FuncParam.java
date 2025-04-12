package compiler.Parser.Grammar;

import java.util.ArrayList;

import static jdk.dynalink.linker.support.Guards.isInstance;

public class FuncParam {
    ArrayList<Type> type;
    String identifier;
    int tabIndex;


    public FuncParam(ArrayList<Type> type, String identifier, int tabIndex) {
        this.type = type;
        this.identifier = identifier;
        this.tabIndex = tabIndex;

    }

    @Override
    public String toString() {
        //TODO implem array list type
        String t = "\t".repeat(tabIndex);
        StringBuilder typeStr = new StringBuilder();
        for (Type type : type) {
            if (type instanceof SimpleType) {
                ((SimpleType) type).tabIndex = tabIndex;
            } else if (type instanceof ArrayDeclarationBracket) {
                ((ArrayDeclarationBracket) type).tabIndex = tabIndex;
            }
            typeStr.append(type);
            System.out.println("la classe = "+ type.getClass()+ " la valeur = "+ type);
        }
        return t + identifier+ "\n" + typeStr;
    }
}
