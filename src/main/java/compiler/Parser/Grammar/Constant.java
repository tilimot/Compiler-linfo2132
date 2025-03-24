package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Constant {
    String final_;
    String identifier;
    SimpleType basetype;
    String equalOperator;
    ArrayList<Expression> expressions;
    String eol;

    public Constant(String final_, String identifier, SimpleType basetype,String equalOperator, ArrayList<Expression> expressions, String eol){
        this.final_ = final_;
        this.identifier = identifier;
        this.basetype = basetype;
        this.equalOperator = equalOperator;
        this.expressions =  expressions;
        this.eol = eol;
    }
}
