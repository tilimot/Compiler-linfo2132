package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Param {
    ArrayList<Expression> expressions = new ArrayList<>();

    public Param(ArrayList<Expression> expressions){
        this.expressions=expressions;
    }
}
