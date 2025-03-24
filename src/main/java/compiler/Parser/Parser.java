package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;

import java.util.ArrayList;

public class Parser {
    private int currentIndex = 0;
    ArrayList<Symbol> allSymbols = new ArrayList<>();
    public Symbol currentSymbol;
    public Symbol root;
    public int tabIndex = 2;

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



    public Symbol match(TokenType token) throws Exception {
        System.out.println("Match: " + token  + " " + this.currentSymbol.getTokenType());
        if (this.currentSymbol.getTokenType() != token) {
            throw new Exception("No match found");
        } else {
            Symbol matchingSymbol = this.currentSymbol;
            this.advance();
            return matchingSymbol;
        }
    }

    public File getAST() throws Exception {
        return parseFile();
    }


    public SimpleType parseSimpleType() throws Exception {
        // TODO manage voidType
        Symbol value;

        if (currentSymbol.getTokenType() == TokenType.BASE_TYPE) {
            value = match(TokenType.BASE_TYPE);
        } else {
            value = match(TokenType.IDENTIFIER);
        }
        return new SimpleType((String) value.getAttribute(),tabIndex );
    }

    public ArrayPart parseArrayPart() throws Exception {
        //TODO need to implement TokenType.OpenParenthesis / .ClosingParenthesis / .OpenBracket / Closing.Bracket

        Symbol left_bracket = match(TokenType.OPERATOR);
        Symbol right_bracket = match(TokenType.OPERATOR);
        return new ArrayPart((String) left_bracket.getAttribute(), (String) right_bracket.getAttribute(), tabIndex);
    }

    public Type parseType() throws Exception {
        //TODO ArrayPart is currently mandatory and should be optional
        SimpleType simpleType = parseSimpleType();
        ArrayPart arrayPart = parseArrayPart();

        return new Type(simpleType, arrayPart,tabIndex );
    }

    public Param parseParam() throws Exception {
        Type type = parseType();
        Symbol identifier = match(TokenType.IDENTIFIER);
        return new Param(type, (String) identifier.getAttribute(),tabIndex );
    }

    public ArrayList<Param> parseParams() throws Exception {
        //TODO need to change TokenType.Operator with .ClosingPArenthesis
        ArrayList<Param> parameters = new ArrayList<>();
        if (currentSymbol.getAttribute() != ")") {
            parameters.add(parseParam());
            while (currentSymbol.getAttribute().equals(",")) {
                match(TokenType.OPERATOR);
                parameters.add(parseParam());
            }
        }
        return parameters;
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
        return new Expression((String) value.getAttribute(),tabIndex );
    }

    public ArrayList<Expression> parseFactor() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        Expression openingParenthesis;
        Expression expression;
        Expression closingParenthesis;

        // Grammar rule:  Factor -> (Expressions) | Expression
        if (currentSymbol.getAttribute().equals("(")){
            openingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute(), tabIndex);
            expression = parseExpression();
            closingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute(), tabIndex);

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
        if(currentSymbol.getAttribute().equals("*")) {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute(), tabIndex);
            factor = parseFactor();

            expressions.add(operatorSign);
            expressions.addAll(factor);
            expressions.addAll(parseTermPrime());
        }
        else if (currentSymbol.getAttribute().equals("/")) {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute(), tabIndex);
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
        if (currentSymbol.getAttribute().equals("+")) {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute(),tabIndex );
            term = parseTerm();

            expressions.add(operatorSign);
            expressions.addAll(term);
            expressions.addAll(parseMoreExpressions());
        }
        else if (currentSymbol.getAttribute().equals("-")) {
            operatorSign = new Expression((String)  match(TokenType.OPERATOR).getAttribute(), tabIndex);
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

    public MethodCall parseMethodCall() throws Exception {
        /*
        * GrammarRule: MethodCall -> identifier(Params) ;
        */

        Symbol identifier = match(TokenType.IDENTIFIER);
        Symbol opening_parenthesis = match(TokenType.OPERATOR);
        ArrayList<Param> params = parseParams();
        Symbol closing_parenthesis = match(TokenType.OPERATOR);
        Symbol eol = match(TokenType.EOL);

        return new MethodCall((String) identifier.getAttribute(), (String) opening_parenthesis.getAttribute(), params, (String) closing_parenthesis.getAttribute(), (String) eol.getAttribute(),tabIndex );
    }

    public VariableDeclaration parseVariableDeclaration() throws Exception {
        /*
         * GrammarRule: VariableDeclaration -> identifier  Type ;
         */

        String identifier = (String) match(TokenType.IDENTIFIER).getAttribute();
        Type type = parseType();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new VariableDeclaration(identifier,type,eol,tabIndex );
    }

    public ReturnStatement parseReturnStatement() throws Exception {
        /*
         * GrammarRule: ReturnStatement -> return Expressions ;
         */

        String return_ = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions = parseExpressions();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new ReturnStatement(return_,expressions,eol, tabIndex);
    }


    public ForStatement parseForStatement() throws Exception {
        /*
         * GrammarRule: ForStatement -> for (identifier, natural_Number, natural_Number, natural_Number ) Block
         */

        //TODO Deeply need to implem BLOCK block; !!!!
        String for_ = (String) match(TokenType.KEYWORD).getAttribute();
        String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        String identifier = (String) match(TokenType.IDENTIFIER).getAttribute();
        String coma1 = (String) match(TokenType.OPERATOR).getAttribute();
        String beginValue = (String) match(TokenType.NATURAL_NUMBER).getAttribute();
        String coma2 = (String) match(TokenType.OPERATOR).getAttribute();
        String endValue = (String) match(TokenType.NATURAL_NUMBER).getAttribute();
        String coma3 = (String) match(TokenType.OPERATOR).getAttribute();
        String stepValue = (String) match(TokenType.NATURAL_NUMBER).getAttribute();
        String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        Block block = parseBlock();

        return new ForStatement(for_, opening_parenthesis, identifier, coma1, beginValue, coma2, endValue, coma3, stepValue, closing_parenthesis, block,tabIndex );
    }


    public WhileStatement parseWhileStatement() throws Exception {
        /*
         * GrammarRule: WhileStatement -> while ( Expressions) Block
         */

        //TODO Deeply need to implem BLOCK block; !!!!
        //TODO develop more specific operator in Lexer !!
        String while_ = (String) match(TokenType.KEYWORD).getAttribute();
        String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions =  parseMoreExpressions();   //Don't manage correctly expression condition as while(true) or while(a == 1)
        String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        Block block = parseBlock();

        return new WhileStatement(while_,opening_parenthesis,expressions,closing_parenthesis, block, tabIndex);
    }


    public IfStatement parseIfStatement() throws Exception {
        /*
         * GrammarRule: IfStatement -> if ( Expressions) Block
         */

        //TODO Deeply need to implem BLOCK block; !!!!
        //TODO develop more specific operator in Lexer !!
        String if_ = (String) match(TokenType.KEYWORD).getAttribute();
        String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions =  parseMoreExpressions();   //Don't manage correctly expression condition as while(true) or while(a == 1)
        String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        Block block = parseBlock();

        return new IfStatement(if_,opening_parenthesis,expressions,closing_parenthesis, block,tabIndex );
    }


    public DeallocationStatement parseDeallocationStatement() throws Exception {
        /*
         * GrammarRule: Deallocation -> free identifier ;
         */

        String free_ = (String) match(TokenType.KEYWORD).getAttribute();;
        String identifier = (String) match(TokenType.IDENTIFIER).getAttribute();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new DeallocationStatement(free_, identifier, eol,tabIndex );
    }

    public FunctionStatement parseFunctionStatement() throws Exception {
        //TODO returnType dont manage constant Type
        String fun_ = match(TokenType.KEYWORD).getAttribute();
        String identifier = match(TokenType.IDENTIFIER).getAttribute();
        String openParenthesis = match(TokenType.OPERATOR).getAttribute();
        Type type = parseType();
        ArrayList<Param> params = parseParams();
        String closingParenthesis = match(TokenType.OPERATOR).getAttribute();
        Type return_type = parseType();
        Block block = parseBlock();

        return new FunctionStatement(fun_, identifier, openParenthesis, type, params, closingParenthesis, return_type, block,tabIndex );
    }


    public AssignementStatement parseAssignementStatement() throws Exception{
        /*
         * GrammarRule: Assignement  final Assignement | identifier Type = Expression ;
         */
        //TODO manage when variable is assigned a method

        if(currentSymbol.getAttribute().equals("final")){
            match(TokenType.KEYWORD); // Currently not appearinf in the AST
        }
        String identifier = (String) match(TokenType.IDENTIFIER).getAttribute();
        Type type = parseType();
        String equalOperator = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions = parseExpressions();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new AssignementStatement(identifier, type, equalOperator, expressions, eol,tabIndex );

    }


    public Statement parseCallOrDeclarationOrAssignement (String identifier) throws Exception{
       //TODO implem le cas ou c'est la définition d'un fonciton
        Statement statement;

        //MethodCall
        if (currentSymbol.getAttribute().equals("(")){
            String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
            ArrayList<Param> params = parseParams();
            String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
            String eol = (String) match(TokenType.EOL).getAttribute();
            statement = new MethodCall(identifier,opening_parenthesis, params, closing_parenthesis, eol, tabIndex);
        }
        else {
            Type type = parseType();

            // Declaration
            if (currentSymbol.getTokenType() == TokenType.EOL) {
                String eol = (String) match(TokenType.EOL).getAttribute();
                statement = new VariableDeclaration(identifier, type, eol,tabIndex );
            }

            // Assignement
            else {
                String equalOperator = (String) match(TokenType.OPERATOR).getAttribute();
                ArrayList<Expression> expressions = parseExpressions();
                String eol = (String) match(TokenType.EOL).getAttribute();
                statement = new AssignementStatement(identifier, type, equalOperator, expressions, eol,tabIndex );
            }
        }


        return statement;
    }


    public Statement parseStatement() throws Exception{
        Statement statement;

        System.out.println("CurrentSymbol: " + currentSymbol.getAttribute());

        if(currentSymbol.getAttribute().equals("if")){
            statement = parseIfStatement();
        }
        else if (currentSymbol.getAttribute().equals("while")) {
            statement = parseWhileStatement();
        }
        else if (currentSymbol.getAttribute().equals("for")) {
            statement = parseForStatement();
        }
        else if (currentSymbol.getAttribute().equals("return")){
            statement = parseReturnStatement();
        }
        else if (currentSymbol.getAttribute().equals("free")){
            statement = parseDeallocationStatement();
        }
        else if (currentSymbol.getAttribute().equals("fun")) {
            statement = parseFunctionStatement();
        }


        else{
            String identifier = match(TokenType.IDENTIFIER).getAttribute();
            statement = parseCallOrDeclarationOrAssignement(identifier);
        }
        return statement;
    }

    public ArrayList<Statement> parseStatements() throws Exception {
        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (!currentSymbol.getAttribute().equals(";")){
            if (currentSymbol.getAttribute().equals("@eof"))
                return statements;
            statements.add(parseStatement());
        }
        return statements;
    }

    public Block parseBlock() throws Exception {
        String leftBracket = match(TokenType.OPERATOR).getAttribute();
        ArrayList<Statement> statements = parseStatements();
        String rightBracket = match(TokenType.OPERATOR).getAttribute();

        return new Block(leftBracket, statements, rightBracket,tabIndex );
    }

    public File parseFile () throws Exception {
        //TODO : loop on each statement of the file
        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (currentSymbol.getTokenType() != TokenType.EOF){
            statements.addAll(parseStatements());
        }
        return new File(statements, tabIndex);
    }



}

