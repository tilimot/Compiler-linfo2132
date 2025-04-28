package compiler.Parser.Grammar;

import java.util.ArrayList;

public class RightSideExpressions extends RightSide {
    public ArrayList<Expression> expressions;
    public RightSideExpressions(ArrayList<Expression> expressions){
        this.expressions= expressions;
    }
}
