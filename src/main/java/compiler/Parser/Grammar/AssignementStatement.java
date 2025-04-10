package compiler.Parser.Grammar;

import java.util.ArrayList;

public class AssignementStatement extends Statement {
    LeftSide leftSide;
    String equalOperator;
    RightSide rightSide;
    String eol;


    public AssignementStatement(LeftSide leftSide, String  equalOperator, RightSide rightSide, String eol, int tabIndex) {
        super(tabIndex);
        this.leftSide=leftSide;
        this.equalOperator = equalOperator;
        this.rightSide=rightSide;
        this.eol = eol;
    }

    @Override
    public String toString() {
        //TODO Implem ArrayList Type print
        String t = "\t".repeat(tabIndex);
        String tNext = t + "\t";
        //type.tabIndex = tabIndex + 1;
        StringBuilder expressionsStr = new StringBuilder();
        /*
        for (Expression expression : expressions) {
            expression.tabIndex = tabIndex + 1;
            expressionsStr.append(expression.toString());
        }*/
        return t +"ASSIGN : " + "\n" +tNext /*+ identifier*/ +  "\n" /*+ type.toString()*/ +tNext+ equalOperator + "\n"+ expressionsStr +tNext + eol+ "\n";

    }

}

