package compiler.Parser;

import compiler.Lexer.Lexer;
import compiler.Lexer.Symbol;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;
import compiler.Parser.Grammar.Record;

import java.util.ArrayList;
import java.util.Objects;

public class Parser {
    private int currentIndex = 0;
    ArrayList<Symbol> allSymbols = new ArrayList<>();
    public Symbol currentSymbol;
    public Symbol root;
    public int tabIndex = 0;

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
            throw new Exception("No match found. Given Token: "+token+"   Current Symbol: "+this.currentSymbol.getTokenType()+ "  value: "+this.currentSymbol.getAttribute());
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
        }
        else if (currentSymbol.getTokenType() == TokenType.VOID_TYPE){
            value = match(TokenType.VOID_TYPE);
        }
        else if (currentSymbol.getTokenType() == TokenType.RECORD_NAME) {
            value = match(TokenType.RECORD_NAME);
        }
        else{
            value = match(TokenType.IDENTIFIER);
        }
        return new SimpleType((String) value.getAttribute(),tabIndex );
    }

    public RecordAttribute parseRecordAttribute() throws Exception {
        Symbol dot = match(TokenType.OPERATOR);
        Symbol attribute = match(TokenType.IDENTIFIER);
        return  new RecordAttribute(dot.getAttribute(), attribute.getAttribute());
    }

    public ArrayAccesBracket parseArrayAccessBracket() throws  Exception {
        Symbol left_bracket = match(TokenType.OPERATOR);
        Symbol integerValue = match(TokenType.NATURAL_NUMBER);
        Symbol right_bracket = match(TokenType.OPERATOR);
        return new ArrayAccesBracket(left_bracket.getAttribute(), integerValue.getAttribute(), right_bracket.getAttribute());
    }

    public ArrayDeclarationBracket parseArrayDeclarationBracket() throws Exception {
        //TODO need to implement TokenType.OpenParenthesis / .ClosingParenthesis / .OpenBracket / Closing.Bracket

        Symbol left_bracket = match(TokenType.OPERATOR);
        Symbol right_bracket = match(TokenType.OPERATOR);
        return new ArrayDeclarationBracket((String) left_bracket.getAttribute(), (String) right_bracket.getAttribute(), tabIndex);
    }

    public ArrayList<Type> parseType() throws Exception {
        ArrayList<Type> types = new ArrayList<>();
        types.add(parseSimpleType());
        if(currentSymbol.getAttribute().equals("[")){
            types.add(parseArrayDeclarationBracket());
        }

        return types;
    }

    public Param parseParam() throws Exception{
        ArrayList<Expression> expressions = parseExpressions();
        return new Param(expressions);
    }

    public ArrayList<Param> parseParams() throws Exception{
        ArrayList<Param> params= new ArrayList<>();
        if (!Objects.equals(currentSymbol.getAttribute(), ")")) {
            params.add(parseParam());
            while (currentSymbol.getAttribute().equals(",")) {
                match(TokenType.OPERATOR);
                params.add(parseParam());
            }
        }
        return params;
    }


    public FuncParam parseFuncParam() throws Exception {
        Symbol identifier = match(TokenType.IDENTIFIER);
        ArrayList<Type> types = parseType();
        return new FuncParam(types, identifier.getAttribute(),tabIndex );
    }

    public ArrayList<FuncParam> parseFuncParams() throws Exception {
        //TODO need to change TokenType.Operator with .ClosingPArenthesis
        ArrayList<FuncParam> parameters = new ArrayList<>();
        if (!currentSymbol.getAttribute().equals(")")) {
            parameters.add(parseFuncParam());
            while (currentSymbol.getAttribute().equals(",")) {
                match(TokenType.OPERATOR);
                parameters.add(parseFuncParam());
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
        else if (currentSymbol.getTokenType() == TokenType.BOOLEAN){
            value = match(TokenType.BOOLEAN);
        }
        else{
            value = match(TokenType.IDENTIFIER);
        }
        return new Expression((String) value.getAttribute(),tabIndex );
    }

    public ArrayList<Expression> parseFactor() throws Exception {
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        Expression openingParenthesis;
        ArrayList<Expression> inside_expressions = new ArrayList<Expression>();
        Expression closingParenthesis;
        Expression expression;

        // Grammar rule:  Factor -> (Expressions) | Expression
        if (currentSymbol.getAttribute().equals("(")){
            openingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute(), tabIndex);
            inside_expressions = parseExpressions();
            closingParenthesis = new Expression((String) match(TokenType.OPERATOR).getAttribute(), tabIndex);

            // Add to list
            expressions.add(openingParenthesis);
            expressions.addAll(inside_expressions);
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

    public ArrayList<Expression> parseConditions() throws Exception {
        String identifier = match(TokenType.IDENTIFIER).getAttribute();
        if (!currentSymbol.getAttribute().equals(")")) {
            if (currentSymbol.getAttribute().equals("=") ||
                    currentSymbol.getAttribute().equals("!") ||
                    currentSymbol.getAttribute().equals("<") ||
                    currentSymbol.getAttribute().equals(">"))
                return parseComparisonCondition(identifier);
        }
        return parseSimpleCondition(identifier);



    }

    public ArrayList<Expression> parseSimpleCondition (String identifier) throws Exception{
        ArrayList<Expression> simpleCondition = new ArrayList<>();
        simpleCondition.add(new Expression(identifier, tabIndex));
        return simpleCondition;
    }

    public ArrayList<Expression> parseComparisonCondition(String identifier) throws Exception{
        ArrayList<Expression> comparisonCondition = new ArrayList<>();
        comparisonCondition.add(new Expression(identifier, tabIndex));
        String operator = match(TokenType.OPERATOR).getAttribute();
        comparisonCondition.add(new Expression(operator, tabIndex));
        if (currentSymbol.getTokenType() == TokenType.OPERATOR && !currentSymbol.getAttribute().equals(")")) {
            String operator2 = match(TokenType.OPERATOR).getAttribute();
            comparisonCondition.add(new Expression(operator2, tabIndex));
        }
        comparisonCondition.addAll(parseExpressions());

        return comparisonCondition;
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
        ArrayList<Type> type = parseType();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new VariableDeclaration(identifier,type,eol,tabIndex );
    }

    public ArrayList<VariableDeclaration> parseMoreVariableDeclaration() throws Exception{
        //must only be used for Record init
        ArrayList<VariableDeclaration> declarations = new ArrayList<VariableDeclaration> ();

        while(currentSymbol.getTokenType() != TokenType.OPERATOR){
            declarations.add(parseVariableDeclaration());
        }
        return declarations;
    }

    public ReturnStatement parseReturnStatement() throws Exception {
        /*
         * GrammarRule: ReturnStatement -> return Expressions ;
         */

        String return_ = (String) match(TokenType.KEYWORD).getAttribute();
        ArrayList<Expression> expressions = parseExpressions();
        String eol = (String) match(TokenType.EOL).getAttribute();
        return new ReturnStatement(return_,expressions,eol,tabIndex);
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
        ArrayList<Expression> conditions =  parseConditions();
        String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        Block block = parseBlock();

        return new WhileStatement(while_,opening_parenthesis,conditions,closing_parenthesis, block, tabIndex);
    }


    public IfStatement parseIfStatement() throws Exception {
        /*
         * GrammarRule: IfStatement -> if ( Expressions) Block
         */

        //TODO Deeply need to implem BLOCK block; !!!!
        //TODO develop more specific operator in Lexer !!
        String if_ = (String) match(TokenType.KEYWORD).getAttribute();
        String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> conditions =  parseConditions();   //Don't manage correctly expression condition as while(true) or while(a == 1)
        String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
        Block block = parseBlock();

        return new IfStatement(if_,opening_parenthesis,conditions,closing_parenthesis, block,tabIndex );
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
        //ArrayList<Type> type = parseType();

        ArrayList<FuncParam> funcParams = parseFuncParams();
        String closingParenthesis = match(TokenType.OPERATOR).getAttribute();
        ArrayList<Type> return_type = parseType();
        Block block = parseBlock();

        return new FunctionStatement(fun_, identifier, openParenthesis, funcParams, closingParenthesis, return_type, block,tabIndex );
    }

/*
    public AssignementStatement parseAssignementStatement() throws Exception{


        String identifier = (String) match(TokenType.IDENTIFIER).getAttribute();

        // It is a reccords attribute access --> x.a= ...
        if (currentSymbol.getAttribute().equals(".")){
            isRecordAttribute = true;
            parseRecordAttribute();
        }
//        else if () {
//
//        }
        ArrayList<Type> type = parseType();


        String equalOperator = (String) match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions = parseExpressions();
        String eol = (String) match(TokenType.EOL).getAttribute();

        return new AssignementStatement(identifier, type,equalOperator, expressions, eol,tabIndex );
    }
*/

    public Statement parseCallOrDeclarationOrAssignment() throws Exception{
        //TODO implem le cas ou c'est la définition d'un fonction
        //revoir l'utilisation de Assignement et Declaration avec les types
        Statement statement;

        String identifier = match(TokenType.IDENTIFIER).getAttribute();

        //MethodCall
        if (currentSymbol.getAttribute().equals("(")){
            String opening_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
            ArrayList<Param> params = parseParams();
            String closing_parenthesis = (String) match(TokenType.OPERATOR).getAttribute();
            String eol = (String) match(TokenType.EOL).getAttribute();
            statement = new MethodCall(identifier,opening_parenthesis, params, closing_parenthesis, eol, tabIndex);
            return statement;
        }
        else{
            //TODO add RecordType
            //Declaration or LeftSideAssignement
            if(currentSymbol.getTokenType().equals(TokenType.BASE_TYPE) || currentSymbol.getTokenType().equals(TokenType.RECORD_NAME)){
                ArrayList<Type> type = parseType() ;

                //Declaration ( x int;)
                if(currentSymbol.getTokenType().equals(TokenType.EOL)){
                    String eol = match(TokenType.EOL).getAttribute();
                    return new VariableDeclaration(identifier,type,eol,tabIndex);
                }
                //LeftSideAssignement : (x int = ...)
                else{
                 LeftSideAssignement leftSide = new LeftSideAssignement(identifier,type);
                 return parseAssignement(leftSide);
                }
            }
            // Record Attribute Access :  ( x.a = ...)
            else if (currentSymbol.getAttribute().equals(".")){
                RecordAttribute recordAttribute = parseRecordAttribute();
                LeftSideRecordAccess leftside = new LeftSideRecordAccess(identifier,recordAttribute);

                return parseAssignement(leftside);
            }

            // ArrayAccess : ( x[0] = .... | x[0].a = ...)
            else{
                String leftBracket = match(TokenType.OPERATOR).getAttribute();
                String index = match(TokenType.NATURAL_NUMBER).getAttribute();
                String rightBracket = match(TokenType.OPERATOR).getAttribute();

                ArrayList<RecordAttribute> recordAttributes = new ArrayList<>();
                while (!currentSymbol.getAttribute().equals("=")){
                    recordAttributes.add(parseRecordAttribute());
                }

                LeftSideArrayAccess leftSide = new LeftSideArrayAccess(identifier, leftBracket, index,rightBracket, recordAttributes);
                return parseAssignement(leftSide);
            }
        }

    }

    public RightSide parseRightSide() throws Exception{
        ArrayList<Expression> expressions = parseExpressions();
        return new RightSideExpressions(expressions);
    }

    public AssignementStatement parseAssignement(LeftSide leftside) throws Exception{
        String equalOperator = match(TokenType.OPERATOR).getAttribute();
        RightSide rightSide = parseRightSide();
        String eol = match(TokenType.EOL).getAttribute();

        return new AssignementStatement(leftside,equalOperator,rightSide,eol,tabIndex);
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
            statement = parseCallOrDeclarationOrAssignment();
        }
        return statement;
    }

    public ArrayList<Statement> parseStatements() throws Exception {
        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (!currentSymbol.getAttribute().equals("}")) {
            System.out.println("PRESENT: " + currentSymbol.getAttribute());
            statements.add(parseStatement());
        }
        return statements;
    }

    public Block parseBlock() throws Exception {
        String leftBracket = match(TokenType.OPERATOR).getAttribute();
        ArrayList<Statement> statements = parseStatements();
        String rightBracket = match(TokenType.OPERATOR).getAttribute();

        return new Block(leftBracket, statements, rightBracket,tabIndex);
    }

    public Constant parseConstant() throws Exception {
        String final_ = match(TokenType.KEYWORD).getAttribute();
        String identifier = match(TokenType.IDENTIFIER).getAttribute();
        ArrayList<Type> basetype = parseType();
        String equalOperator = match(TokenType.OPERATOR).getAttribute();
        ArrayList<Expression> expressions = parseExpressions();
        String eol = match(TokenType.EOL).getAttribute();

        return new Constant(final_,identifier,basetype,equalOperator,expressions,eol,tabIndex);
    }

    public ArrayList<Constant> parseMoreConstant() throws Exception {
        ArrayList<Constant> constants = new ArrayList<>();
        while (currentSymbol.getAttribute().equals("final")){
            constants.add(parseConstant());
        }
        return constants;
    }


    public Record parseRecord() throws Exception {
        String recordsName = match(TokenType.RECORD_NAME).getAttribute();
        String rec_ =  match(TokenType.KEYWORD).getAttribute();
        String openingBracket = match(TokenType.OPERATOR).getAttribute();
        ArrayList<VariableDeclaration> declarations = parseMoreVariableDeclaration();
        String closingBracket = match(TokenType.OPERATOR).getAttribute();

        return new Record(recordsName, rec_, openingBracket, declarations, closingBracket, tabIndex);
    }

    public ArrayList<Record> parseMoreRecord() throws Exception{
        ArrayList<Record> records = new ArrayList<Record>();
        while(currentSymbol.getTokenType() == TokenType.RECORD_NAME){
            records.add(parseRecord());
        }
        return records;
    }


    public ArrayList<Statement> parseMoreGlobalVariables() throws Exception {
        ArrayList<Statement> globalVariables = new ArrayList<Statement>();
        while(currentSymbol.getTokenType() == TokenType.IDENTIFIER){
            globalVariables.add(parseCallOrDeclarationOrAssignment());
        }
        return globalVariables;
    }


    public File parseFile () throws Exception {

        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (currentSymbol.getTokenType() != TokenType.EOF){
            statements.addAll(parseStatements());
        }
        return new File(statements, tabIndex);
    }

    public ArrayList<FunctionStatement> parseFunctionInit() throws Exception {
        ArrayList<FunctionStatement> functions = new ArrayList<FunctionStatement>();

        while(currentSymbol.getTokenType() != TokenType.EOF){
            functions.add(parseFunctionStatement());
        }

        return functions;
    }


    public Ast parseAst() throws Exception {
        ArrayList<Constant> constants = parseMoreConstant();
        ArrayList<Record> records = parseMoreRecord();
        ArrayList<Statement> globalVariables = parseMoreGlobalVariables();
        ArrayList<FunctionStatement>  functions = parseFunctionInit();

        return new Ast(constants,records,globalVariables, functions, tabIndex);
    }

    public Ast getAst() throws Exception{
        return parseAst();
    }

}

