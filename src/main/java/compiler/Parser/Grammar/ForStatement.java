package compiler.Parser.Grammar;

public class ForStatement extends Statement {
    String for_;
    String opening_parenthesis;
    String identifier;
    String coma1;
    String beginValue;
    String coma2;
    String endValue;
    String coma3;
    String stepValue;
    String closing_parenthesis;
    Block block;
    public ForStatement (String for_, String opening_parenthesis, String identifier, String coma1, String beginValue, String coma2, String endValue, String coma3, String stepValue, String closing_parenthesis, Block block){
        this.for_= for_;
        this.opening_parenthesis = opening_parenthesis;
        this.identifier = identifier;
        this.coma1 = coma1;
        this.beginValue = beginValue;
        this.coma2 = coma2;
        this.endValue = endValue;
        this.coma3 = coma3;
        this.stepValue = stepValue;
        this.closing_parenthesis = closing_parenthesis;
        this.block = block;
    }

}
