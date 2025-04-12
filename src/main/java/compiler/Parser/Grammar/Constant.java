package compiler.Parser.Grammar;

import java.util.ArrayList;

public class Constant {
    String final_;
    String identifier;
    ArrayList<Type> basetype;
    String equalOperator;
    ArrayList<Expression> expressions;
    String eol;
    int tabIndex;

    public Constant(String final_, String identifier, ArrayList<Type> basetype, String equalOperator, ArrayList<Expression> expressions, String eol, int tabIndex) {
        this.final_ = final_;
        this.identifier = identifier;
        this.basetype = basetype;
        this.equalOperator = equalOperator;
        this.expressions =  expressions;
        this.eol = eol;
        this.tabIndex = tabIndex;
    }

    @Override
    public String toString() {
        String t = "\t".repeat(tabIndex);
        String tNext = t + "\t";
        StringBuilder expressionsStr = new StringBuilder();
        //basetype.tabIndex = tabIndex + 1;
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex + 1;
            expressionsStr.append(expression);
        }
        StringBuilder basetypeStr = new StringBuilder();
        for (Type type : basetype) {
            if (type instanceof SimpleType) {
                ((SimpleType) type).tabIndex = tabIndex + 1;
            } else if (type instanceof ArrayDeclarationBracket) {
                ((ArrayDeclarationBracket) type).tabIndex = tabIndex + 1;
            }
            basetypeStr.append(type);
        }

        return t +"CONST : " + "\n" +tNext + final_ + "\n" +tNext + identifier +  "\n" + basetypeStr + "\n" +tNext+ equalOperator + "\n"+ tNext + "EXPR :" + "\n" +expressionsStr +tNext + eol+ "\n";
    }
}
