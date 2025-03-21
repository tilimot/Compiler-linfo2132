package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.Assignement;

import java.util.ArrayList;

public class Parser {
    private int currentIndex = 0;
    ArrayList<Symbol> allSymbols = new ArrayList<>();
    public Parser(Lexer lexer) {
        while (lexer.hasNextSymbol()) {
            allSymbols.add(lexer.getNextSymbol());
        }
        System.out.println("Parser: " + allSymbols);
    }

    public Symbol currentSymbol() {
        if (currentIndex < allSymbols.size()) {
            return allSymbols.get(currentIndex);
        }
        return null; // or a special EOF symbol
    }

    public void advance() {
        currentIndex++;
    }

    public Matcher match() throws Exception {

        while (currentSymbol() != null) {
            switch (currentSymbol().getTokenType()){
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



}
