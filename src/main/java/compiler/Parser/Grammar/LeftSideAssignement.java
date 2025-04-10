package compiler.Parser.Grammar;

import java.util.ArrayList;

public class LeftSideAssignement extends LeftSide{
    String identifier;
    ArrayList<Type> type;

    public LeftSideAssignement(String identifier, ArrayList<Type> type){
        this.identifier = identifier;
        this.type = type;
    }
}
