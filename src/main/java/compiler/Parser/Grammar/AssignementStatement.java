package compiler.Parser.Grammar;

import compiler.Exception.OperatorException;
import compiler.Exception.TypeException;
import compiler.Lexer.TokenType;
import compiler.Semantic.Semantic;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignementStatement extends Statement {
    public LeftSide leftSide;
    String equalOperator;
    public RightSide rightSide;
    String eol;


    public AssignementStatement(LeftSide leftSide, String  equalOperator, RightSide rightSide, String eol, int tabIndex) {
        super(tabIndex);
        this.leftSide=leftSide;
        this.equalOperator = equalOperator;
        this.rightSide=rightSide;
        this.eol = eol;
    }

    public void semanticAnalysis(HashMap<String, Type> st) throws Exception {
        Semantic.checkGlobalDecl(this);
        st.put(((LeftSideAssignement)leftSide).identifier, ((LeftSideAssignement)leftSide).type.getFirst());

        Semantic.checkRefToVariable(((LeftSideAssignement)leftSide).identifier,((RightSideExpressions)rightSide).expressions);



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




        return t +"ASSIGN : " + "\n"  + leftSide +  "\n" +tNext+ equalOperator + "\n" + tNext + "EXPR :"+ "\n"+
                expressionsStr +tNext + eol+ "\n";

    }

}

