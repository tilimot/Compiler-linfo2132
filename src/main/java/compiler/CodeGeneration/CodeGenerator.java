package compiler.CodeGeneration;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;
import compiler.Parser.Grammar.Record;
import compiler.Parser.Grammar.Type;
import org.junit.experimental.theories.internal.Assignments;
import org.objectweb.asm.*;
import java.io.IOException;
import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;

import static org.objectweb.asm.Opcodes.*;
import compiler.Parser.*;

/*
* Currently, everything is written as if the AST was only made up of an Expression
* s.t it is its own type (and Not AST type)
*/


public class CodeGenerator{
    ClassWriter cw;
    String generatedClass;
    Ast ast; //TODO: replace by AST at the end
    IndexTable indexTable;


    public CodeGenerator(String generatedClass,Ast ast){
        this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        this.generatedClass=generatedClass;
        this.ast=ast; //TODO: replace by AST at the end
        this.indexTable = new IndexTable(null);
    }


    public void generateFileClass() throws Exception{

        // CreateClass
        this.cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "java/lang/Object", null);


        // Ast grammar: AST -> Constants Records GlobalVariables Functions
        Ast ast = this.ast;

        // Begin Static bloc --> to store static variable
        MethodVisitor clinit = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);


        // Constants
        ArrayList<Constant> csts = ast.getConstant();
        if(!csts.isEmpty()){
            generateMoreConstant(this.cw, csts, clinit);
        }

        // Records
        ArrayList<Record> records = ast.getRecords();

        // Global Variable
        ArrayList<Statement> globalVariables = ast.getGlobalVariables();
        if (!globalVariables.isEmpty()) {
           generateMoreGlobalVariable(this.cw, globalVariables, clinit);
        }

        // End static bloc
        clinit.visitInsn(RETURN);
        clinit.visitMaxs(0, 0);
        clinit.visitEnd();

        // Functions
        ArrayList<FunctionStatement> functions = ast.getFunctions();
        if(!functions.isEmpty()){
            generateMoreFunction(this.cw,functions, this.indexTable);
        }

        cw.visitEnd();
        // generateFile into Bytecode
        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
    }


    public String getTypeDescriptor(TokenType type) throws Exception {
        /**
         * @param type: A TokenType  .
         * @return Return the corresponding TypeDescriptor (used by ASM)
         */
        if (type.equals(TokenType.INTEGER)){
            return "I";
        }
        else if(type.equals(TokenType.FLOAT)){
            return "F";
        }
        else if(type.equals(TokenType.STRINGS)){
            return "Ljava/lang/String;";
        }
        else if(type.equals(TokenType.BOOLEAN)){
            //Booleans
            return "Z";
        }
        else{
            throw new Exception("Code generation error. Non supported type provided: "+ type);
        }
    }


    public void generateMoreGlobalVariable(ClassWriter cw, ArrayList<Statement> globalVariables, MethodVisitor clinit ) throws Exception {
        //TODO: Must call different generate Assignement depending if this is a simple variable assignment (i.e: a int= 1+2), array attribution (i.e: c int[]= array [5]), or else
        //Currently consider only simple variable assignement (i.e: a int= 1+2). Miss array assignement, records attribute assignement, declaration


        clinit.visitCode();

        // Adding fields:  public static x=10
        for(Statement globVar: globalVariables) {
            generateGlobalAssignmentVariable(cw, clinit,  globVar);
        }

    }


    public void generateGlobalAssignmentVariable(ClassWriter cw, MethodVisitor clinit,Statement globalVariable) throws Exception {

        AssignementStatement assignment = (AssignementStatement) globalVariable;

        // LeftSide - store identifier in IndexTable
        LeftSide ls = assignment.leftSide;

        String identifier = ls.getIdentifier();

        TokenType tokenType = ls.getType().getType(); // 1st get type to have the Type object, the 2nd is to have the TokenType from the type
        String td = this.getTypeDescriptor(tokenType);


        // RightSide - generate expression
        RightSideExpressions rs = (RightSideExpressions) assignment.rightSide;
        ArrayList<Expression> expressions = rs.getExpressions();


        // Add field: static type identifier;
        cw.visitField( ACC_PUBLIC + ACC_STATIC, identifier, td, null, null).visitEnd();

        // Generate the expression
        generateExpression(clinit, expressions, indexTable,"classVar", td);

        // Initialize the field
        clinit.visitFieldInsn(Opcodes.PUTSTATIC, this.generatedClass, identifier, td);

    }

    public void generateMoreConstant(ClassWriter cw, ArrayList<Constant> constants, MethodVisitor clinit ) throws Exception{


        clinit.visitCode();

        // Adding fields:  public static x=10
        for( Constant cst: constants) {
            generateConstant(cw, clinit, cst);
        }

    }

    public void generateConstant(ClassWriter cw, MethodVisitor clinit, Constant cst) throws Exception{

        String identifier = cst.identifier;
        String td = this.getTypeDescriptor(cst.basetype.get(0).getType());
        ArrayList<Expression> expressions =  cst.expressions;

        // Add field: static type identifier;
        cw.visitField( ACC_FINAL+ACC_PUBLIC + ACC_STATIC, identifier, td, null, null).visitEnd();

        // Generate the expression
        generateExpression(clinit, expressions, indexTable,"classVar", td);

        // Initialize the field
        clinit.visitFieldInsn(Opcodes.PUTSTATIC, this.generatedClass, identifier, td);

    }

    public void generateMoreFunction(ClassWriter cw, ArrayList<FunctionStatement> functions, IndexTable indexTable) throws Exception{

        for (FunctionStatement function: functions){
            IndexTable funcIndexTable = new IndexTable(indexTable);
            generateFunction(cw, function, funcIndexTable);
        }
    }


    public void generateFunction(ClassWriter cw, FunctionStatement function, IndexTable indexTable) throws Exception {

        String methodName = function.getIdentifier();
        String returnTypeDescriptor = getTypeDescriptor(function.getReturn_type().get(0).getType());
        Block block = function.getBlock();

        // Get params TypeDescriptor - quelle idée de le gérer comme ça aussi
        String paramsTypeDescriptor = "";
        ArrayList<FuncParam> params = function.getParams();
        for(FuncParam param:params){
            String paramTD = getTypeDescriptor(param.getType().get(0).getType());
            paramsTypeDescriptor += paramTD;
        }

        String descriptor = "("+paramsTypeDescriptor+")"+returnTypeDescriptor;
        System.out.println(descriptor);
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC , methodName, descriptor, null, null);
        mv.visitCode();


        // Load Param on Stack and store them on IndexTable
        for(FuncParam param:params){
            String identifier = param.getIdentifier();
            indexTable.addIdentifier(identifier);
            int varIndex = indexTable.getIndexIdentifier(identifier);
            //mv.visitVarInsn(ISTORE, varIndex);
        }



        generateBlock(mv, block, indexTable, returnTypeDescriptor);
        mv.visitVarInsn(ISTORE, 1);

        // Fin de la méthode
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    public void generateBlock(MethodVisitor mv, Block block, IndexTable indexTable, String returnTypeDescriptor) throws Exception {

        ArrayList<Statement> statements = block.getStatements();

        for(Statement statement: statements) {

            if (Statement.iAssignStatement(statement)){
                generateAssignmentStatement(mv, statement, indexTable);
            }
            else if(Statement.isReturnStatement(statement)){
                generateReturnStatement(mv, statement, indexTable, returnTypeDescriptor);
            }
        }



    }

    public void generateAssignmentStatement(MethodVisitor mv, Statement stmt, IndexTable indexTable) throws Exception {
        /**
         * Manage the following Assignement:
         *      VarAssignement :
         *          - a int = 2;
         *          - a str = mtdcall();
         * */

        AssignementStatement assignement = (AssignementStatement) stmt;
        if (Statement.isVarAssignStatement(assignement)){
            generateVariableAssignment(mv, assignement, indexTable);
        }
        else if ( Statement.isVarReassignStatement(assignement)){
            generateVarReassignStatement(mv, assignement, indexTable);
        }

    }

    public void generateVariableAssignment(MethodVisitor mv,AssignementStatement assignment, IndexTable indexTable) throws Exception {

        // RightSide - store identifier in IndexTable
        LeftSide ls = assignment.leftSide;
        String identifier = ls.getIdentifier();
        indexTable.addIdentifier(identifier);

        // LeftSide - generate expression
        RightSideExpressions rs = (RightSideExpressions) assignment.rightSide;
        generateExpression(mv, rs.expressions, indexTable,"funcVar", "");

        // Store the result
        int varindex = indexTable.getIndexIdentifier(identifier);
        mv.visitVarInsn(ISTORE , varindex);

    }

    public void generateVarReassignStatement(MethodVisitor mv,AssignementStatement assignment, IndexTable indexTable) throws Exception {
        // RightSide - store identifier in IndexTable
        LeftSide ls = assignment.leftSide;
        String identifier = ls.getIdentifier();

        // LeftSide - generate expression
        RightSideExpressions rs = (RightSideExpressions) assignment.rightSide;
        generateExpression(mv, rs.expressions, indexTable,"funcVar", "");

        // Store the result
        int varindex = indexTable.getIndexIdentifier(identifier);
        mv.visitVarInsn(ISTORE , varindex);

    }


    public void generateReturnStatement(MethodVisitor mv, Statement stmt, IndexTable indexTable, String returnTypeDescriptor) throws Exception {

        ReturnStatement rtrnStmt = (ReturnStatement) stmt;
        ArrayList<Expression> expressions = rtrnStmt.getReturn();
        generateExpression(mv, expressions, indexTable,"funcVar","");


        if(returnTypeDescriptor.equals("I")){
            mv.visitInsn(IRETURN);
        }
        else if (returnTypeDescriptor.equals("F")){
            mv.visitInsn(FRETURN);
        } else {
            mv.visitInsn(ARETURN);
        }

    }


    public void generateExpression(MethodVisitor mv, ArrayList<Expression> expressions, IndexTable indexTable, String statementType, String typeDescriptor) throws Exception {
        /**
         * Add on stack each element of the expression.
         * When term =2, apply the given operation between the 2 elements on stack
         *
         * @param statementType  Allow to know if the value of a variable is stored on the IndexTable or in the field table class.
         *                     "classVar": GlobalVariable or Constant -> value stored on field table
         *                     "funcVar": Regular       -> value stored in indexTable
         * @param identifier  Identifier of the variable. Used only to retrieve in value of variable in case of statementType = "classVar".
         *                    If no "classVar", can be set to "".
         * @param typeDescriptor typeD of the variable. Used only to retrieve in value of variable in case of statementType = "classVar".
         *                       If no "classVar", can be set to "".
         */


        // Currently only manage Sum of int
        Boolean addition = false;
        Boolean substraction = false;
        Boolean multiplication = false;
        Boolean division = false;
        int term = 0;

        for(Expression expr:expressions){
            String val = expr.getValue();
            TokenType tp = expr.getType();

            if (expr.getValue().equals("+")){
                addition = true;
            }
            else if(expr.getValue().equals("-")){
                substraction=true;
            }
            else if(expr.getValue().equals("*")){
                multiplication=true;
            }
            else if(expr.getValue().equals("/")){
                division=true;
            }
            else if (tp.equals(TokenType.INTEGER)) {
                int intValue =  Integer.valueOf(val);
                mv.visitLdcInsn(intValue);
                term +=1;
            }
            else if(tp.equals(TokenType.FLOAT)) {
                float floatValue = Float.valueOf(val);
                mv.visitLdcInsn(floatValue);
                term +=1;
            }
            else if(tp.equals(TokenType.BOOLEAN)) {
                boolean boolValue;
                if(val.equals("true")){
                    boolValue = true;
                }
                else{
                    boolValue = false;
                }
                mv.visitLdcInsn(boolValue);
                term +=1;
            }
            else if(tp.equals(TokenType.STRINGS)) {
                mv.visitLdcInsn(val);
                term +=1;
            }
            else if(tp.equals(TokenType.IDENTIFIER)){
                if (statementType.equals("funcVar")) {
                    int varIndex = indexTable.getIndexIdentifier(val);
                    mv.visitVarInsn(ILOAD, varIndex);
                    term += 1;
                }
                else if(statementType.equals("classVar")){
                    mv.visitFieldInsn(GETSTATIC, this.generatedClass, val, typeDescriptor);
                    term+=1;
                }
                else{
                    throw new Exception("Issue in CodeGeneration. WrongS StatementType parameters in generateExpression Method");
                }
            }

            if(term==2){ // if there is 2 terms, it means there must be an operation
                term=1; // bc there is now the previous elemement

                // try to know which operation
                if (addition.equals(true)){
                    addition=false;
                    mv.visitInsn(IADD); // Additionate stack elements
                }
                else if (substraction.equals(true)){
                    substraction=false;
                    mv.visitInsn(ISUB); // Substract stack elements
                }
                else if (multiplication.equals(true)) {
                    multiplication=false;
                    mv.visitInsn(IMUL); // Multiplication stack elements
                }
                else if (division.equals(true)) {
                    division=false;
                    mv.visitInsn(IDIV); // Division stack elements
                }

            }
        }

    }


    /*

    public void generateMainMethod() throws Exception {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // Should be the inverse. GenerateAST should call generateMainMethod
        generateAST(mv,this.ast, this.indexTable);


        // Fin de la méthode
        mv.visitInsn(RETURN);

        //mv.visitMaxs(100, indexTable.getCurrent_index());
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
    }


    }*/


}