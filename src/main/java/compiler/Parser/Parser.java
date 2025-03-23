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
        if(this.currentSymbol.getTokenType()!=token) {
            throw new Exception("No match found");
        } else {
            Symbol matchingSymbol = this.currentSymbol;
            this.advance();
            return matchingSymbol;
        }
    }


    public SimpleType parseSimpleType() throws Exception {
        // TODO manage voidType
        Symbol value ;

        if (currentSymbol.getTokenType() == TokenType.BASE_TYPE){
            value = match(TokenType.BASE_TYPE);
        }
        else {
            value = match(TokenType.IDENTIFIER);
        }
        return new SimpleType((String) value.getAttribute());
    }

    public ArrayPart parseArrayPart() throws Exception {
        //TODO need to implement TokenType.OpenParenthesis / .ClosingParenthesis / .OpenBracket / Closing.Bracket

        Symbol left_bracket= match(TokenType.OPERATOR);
        Symbol right_bracket = match(TokenType.OPERATOR);
        return new ArrayPart((String) left_bracket.getAttribute(), (String) right_bracket.getAttribute());
    }

    public Type parseType() throws Exception {
        //TODO ArrayPart is currently mandatory and should be optional
        SimpleType simpleType = parseSimpleType();
        ArrayPart arrayPart = parseArrayPart();

        return new Type(simpleType, arrayPart);
    }

    public Literal parseLiteral() throws Exception {
        Symbol value;
        if (currentSymbol.getTokenType() == TokenType.NATURAL_NUMBER){
            value = match(TokenType.NATURAL_NUMBER);
        }
        else if (currentSymbol.getTokenType() == TokenType.FLOAT_NUMBER) {
            value = match(TokenType.FLOAT_NUMBER);
        }
        else{
            value = match(TokenType.STRINGS);
        }
        return new Literal((String) value.getAttribute());
    }

    public Param parseParam() throws Exception {
        Type type = parseType();
        Symbol identifier = match(TokenType.IDENTIFIER);
        return new Param(type,(String)identifier.getAttribute());
    }

    public ArrayList<Param> parseParams() throws Exception {
        //TODO need to change TokenType.Operator with .ClosingPArenthesis
        ArrayList<Param> parameters = new ArrayList<>();
        if(currentSymbol.getAttribute() != ")") {
            parameters.add(parseParam());
            while(currentSymbol.getAttribute() == ",") {
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


}
