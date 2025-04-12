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
        if (leftSide instanceof LeftSideAssignement) {
            ((LeftSideAssignement) leftSide).tabIndex = tabIndex + 1;
        }
        if (leftSide instanceof LeftSideRecordAccess) {
            ((LeftSideRecordAccess) leftSide).tabIndex = tabIndex + 1;
        }
        if (leftSide instanceof LeftSideArrayAccess) {
            ((LeftSideArrayAccess) leftSide).tabIndex = tabIndex + 1;
        }
        StringBuilder expressionsStr = new StringBuilder();
        for (Expression expression : ((RightSideExpressions) rightSide).expressions) {
            expression.tabIndex = tabIndex + 1;
            expressionsStr.append(expression);

        }

        System.out.println("instance de + leftSide : " + leftSide.getClass());



        return t +"ASSIGN : " + "\n"  + leftSide +  "\n" +tNext+ equalOperator + "\n" + tNext + "EXPR :"+ "\n"+
                expressionsStr +tNext + eol+ "\n";

    }

}

