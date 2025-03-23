package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;

import java.text.ParseException;
import java.util.ArrayList;

public class Parser {
    private int currentIndex = 0;
    ArrayList<Symbol> allSymbols = new ArrayList<>();
    public Symbol currentSymbol;

    public Parser(Lexer lexer) {
        while (lexer.hasNextSymbol()) {
            allSymbols.add(lexer.getNextSymbol());
        }
        this.currentSymbol = getCurrentSymbol();
        //System.out.println("Parser: " + allSymbols);
    }

    public Symbol getCurrentSymbol() {
        if (currentIndex < allSymbols.size()) {
            return allSymbols.get(currentIndex);
        }
        return null; // or a special EOF symbol
    }

    public void advance() {
        this.currentIndex++;
        this.currentSymbol = getCurrentSymbol();
    }

    /*
    public Matcher match() throws Exception {

        while (this.currentSymbol != null) {
            switch (this.currentSymbol.getTokenType()){
                case BASE_TYPE:
                    Assignement assignement = new Assignement(this);
                    return assignement.parseAssignement();
                case BOOLEAN:
                case COMMENT:
                case EOL:
                case KEYWORD:
                case IDENTIFIER:
                case NATURAL_NUMBER:
                case FLOAT_NUMBER:
                case STRINGS:
                case OPERATOR:
            }

    }
        return null;
    }
     */

    public Symbol match(TokenType token) throws Exception {
        if (this.currentSymbol.getTokenType() != token) {
            throw new Exception("No match found");
        } else {
            Symbol matchingSymbol = this.currentSymbol;
            this.advance();
            return matchingSymbol;
        }
    }


    public SimpleType parseSimpleType() throws Exception {
        // TODO manage voidType
        Symbol value;

        if (currentSymbol.getTokenType() == TokenType.BASE_TYPE) {
            value = match(TokenType.BASE_TYPE);
        } else {
            value = match(TokenType.IDENTIFIER);
        }
        return new SimpleType((String) value.getAttribute());
    }

    public ArrayPart parseArrayPart() throws Exception {
        //TODO need to implement TokenType.OpenParenthesis / .ClosingParenthesis / .OpenBracket / Closing.Bracket

        Symbol left_bracket = match(TokenType.OPERATOR);
        Symbol right_bracket = match(TokenType.OPERATOR);
        return new ArrayPart((String) left_bracket.getAttribute(), (String) right_bracket.getAttribute());
    }

    public Type parseType() throws Exception {
        //TODO ArrayPart is currently mandatory and should be optional
        SimpleType simpleType = parseSimpleType();
        ArrayPart arrayPart = parseArrayPart();

        return new Type(simpleType, arrayPart);
    }

    public Param parseParam() throws Exception {
        Type type = parseType();
        Symbol identifier = match(TokenType.IDENTIFIER);
        return new Param(type, (String) identifier.getAttribute());
    }

    public ArrayList<Param> parseParams() throws Exception {
        //TODO need to change TokenType.Operator with .ClosingPArenthesis
        ArrayList<Param> parameters = new ArrayList<>();
        if (currentSymbol.getAttribute() != ")") {
            parameters.add(parseParam());
            while (currentSymbol.getAttribute() == ",") {
                match(TokenType.OPERATOR);
                parameters.add(parseParam());
            }
        }
        return parameters;
    }

    public MethodCall parseMethodCall() throws Exception {
        Symbol identifier = match(TokenType.IDENTIFIER);
        Symbol opening_parenthesis = match(TokenType.OPERATOR);
        ArrayList<Param> params = parseParams();
        Symbol closing_parenthesis = match(TokenType.OPERATOR);

        return new MethodCall((String) identifier.getAttribute(), (String) opening_parenthesis.getAttribute(), params, (String) closing_parenthesis.getAttribute());
    }

    public Expression parseExpression() throws Exception {
        Symbol value;
        if (currentSymbol.getTokenType() == TokenType.NATURAL_NUMBER) {
            value = match(TokenType.NATURAL_NUMBER);
        } else if (currentSymbol.getTokenType() == TokenType.FLOAT_NUMBER) {
            value = match(TokenType.FLOAT_NUMBER);
        } else if(currentSymbol.getTokenType() == TokenType.STRINGS){
            value = match(TokenType.STRINGS);
        }
        else {
            value = match(TokenType.BOOLEAN);
        }
        return new Expression((String) value.getAttribute());
    }

    public ArrayList<Expression> parseFactor() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        Expression openingParenthesis;
        Expression expression;
        Expression closingParenthesis;

        // Grammar rule:  Factor -> (Expressions) | Expression
        if (currentSymbol.getAttribute() == "("){
            openingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute());
            expression = parseExpression();
            closingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute());

            // Add to list
            expressions.add(openingParenthesis);
            expressions.add(expression);
            expressions.add(closingParenthesis);
        }
        else{
            expression = parseExpression();
            expressions.add(expression);
        }
        return expressions;
    }


    public ArrayList<Expression> parseTermPrime() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        Expression operatorSign;
        ArrayList<Expression> factor;

        // Grammar rule: Term' -> * Factor Term’ | /  Factor Term’ | epsilon
        if(currentSymbol.getAttribute() ==  "*") {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute());
            factor = parseFactor();

            expressions.add(operatorSign);
            expressions.addAll(factor);
            expressions.addAll(parseTermPrime());
        }
        else if (currentSymbol.getAttribute() ==  "/") {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute());
            factor = parseFactor();

            expressions.add(operatorSign);
            expressions.addAll(factor);
            expressions.addAll(parseTermPrime());
        }

        return expressions;
    }

    public ArrayList<Expression> parseTerm() throws Exception {
        ArrayList<Expression> expressions = new  ArrayList<Expression>();

        // Grammar rule: Term -> Factor Term'
        expressions.addAll(parseFactor());
        expressions.addAll(parseTermPrime());

        return expressions;
    }

    public ArrayList<Expression> parseMoreExpressions() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        Expression operatorSign;
        ArrayList<Expression> term;


        //Grammar rule : MoreExpressions  -> +Term MoreExpression | - Term MoreExpression | epsilon
        if (currentSymbol.getAttribute() == "+") {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute());
            term = parseTerm();

            expressions.add(operatorSign);
            expressions.addAll(term);
            expressions.addAll(parseMoreExpressions());
        }
        else if (currentSymbol.getAttribute() == "-") {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute());
            term = parseTerm();

            expressions.add(operatorSign);
            expressions.addAll(term);
            expressions.addAll(parseMoreExpressions());
        }

        return expressions;
    }

    public ArrayList<Expression> parseExpressions() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();

        // Grammar rule : Expression -> Term MoreExpression
        expressions.addAll(parseTerm());
        expressions.addAll(parseMoreExpressions());

        return expressions;
    }

}

