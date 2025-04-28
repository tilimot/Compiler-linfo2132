package compiler.Parser.Grammar;

import compiler.Exception.*;
import compiler.Lexer.TokenType;
import compiler.Semantic.Semantic;
import compiler.Semantic.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;


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

    public void semanticAnalysis(HashMap<String, Type> st) throws Exception{

        System.out.println("Analysing constant: " + identifier);

        ArrayList<TokenType> expressionType = new ArrayList<>();
        for (Expression expression : expressions) {
            expressionType.add(expression.getType());
        }
        if (Semantic.checkExpressionsType(expressionType)) {
            throw new OperatorException();
        }
        expressionType.addFirst(basetype.getFirst().getType());
        if (Semantic.checkExpressionsType(expressionType)) {
            throw new TypeException();
        }
        if (Semantic.checkIdentifier(identifier)) {
            throw new FinalException(identifier);
        }
        st.put(identifier, basetype.getFirst());
        
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
