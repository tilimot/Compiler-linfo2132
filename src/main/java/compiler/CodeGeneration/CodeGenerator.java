package compiler.CodeGeneration;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;
import compiler.Parser.Grammar.Record;
import compiler.Parser.Grammar.Type;
import org.junit.experimental.theories.internal.Assignments;
import org.objectweb.asm.*;
import java.io.IOException;
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
        this.indexTable = new IndexTable(null,0);
    }


    public void generateFileClass() throws Exception{

        // CreateClass
        this.cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "java/lang/Object", null);


        // Ast grammar: AST -> Constants Records GlobalVariables Functions
        Ast ast = this.ast;


        // Generate elements of AST
        ArrayList<Constant> cst = ast.getConstant();
        ArrayList<Record> records = ast.getRecords();

        // generate Global Variable only if this is not empty
        ArrayList<Statement> globalVariables = ast.getGlobalVariables();
        if (!globalVariables.isEmpty()) {
            // Static Bloc : static {x = 10;}
           generateMoreGlobalVariable(this.cw, globalVariables);
        }

        ArrayList<FunctionStatement> functions = ast.getFunctions();


        //generateMainMethod(); //TODO: should generate AST and generateAST should generateMAIN


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


    public void generateMoreGlobalVariable(ClassWriter cw, ArrayList<Statement> globalVariables ) throws Exception {
        // Static Bloc : static {x = 10; ...}
        MethodVisitor clinit = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        clinit.visitCode();

        // Adding fields:  public static x=10
        for(Statement globVar: globalVariables) {
            generateGlobalAssignmentVariable(cw, clinit,  globVar);
        }
        clinit.visitInsn(RETURN);
        clinit.visitMaxs(0, 0);
        clinit.visitEnd();
    }


    public void generateGlobalAssignmentVariable(ClassWriter cw, MethodVisitor clinit,Statement globalVariable) throws Exception {
        //TODO: Must call different generate Assignement depending if this is a simple variable assignment (i.e: a int= 1+2), array attribution (i.e: c int[]= array [5]), or else
        //Currently consider only simple variable assignement (i.e: a int= 1+2). Miss array assignement, records attribute assignement, declaration

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
        generateExpression(clinit, expressions,"gv", td);

        // Initialize the field
        clinit.visitFieldInsn(Opcodes.PUTSTATIC, this.generatedClass, identifier, td);

    }


    public void generateExpression(MethodVisitor mv, ArrayList<Expression> expressions, String statementType, String typeDescriptor) throws Exception {
        /**
         * Add on stack each element of the expression.
         * When term =2, apply the given operation between the 2 elements on stack
         *
         * @param statementType  Allow to know if the value of a variable is stored on the IndexTable or in the field table class.
         *                     "gv": GlobalVariable -> value stored on field table
         *                     "reg": Regular       -> value stored in indexTable
         * @param identifier  Identifier of the variable. Used only to retrieve in value of variable in case of statementType = "gv".
         *                    If no "gv", can be set to "".
         * @param typeDescriptor typeD of the variable. Used only to retrieve in value of variable in case of statementType = "gv".
         *                       If no "gv", can be set to "".
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
                if (statementType.equals("reg")) {
                    int varIndex = indexTable.getIndexIdentifier(val);
                    mv.visitVarInsn(ILOAD, varIndex);
                    term += 1;
                }
                else if(statementType.equals("gv")){
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


    public void generateFunction(MethodVisitor mv, ArrayList<FunctionStatement> functions, IndexTable indexTable){


        int currentVarIndex = indexTable.getCurrent_index();
        for(FunctionStatement function: functions) {
            String methodName = function.getIdentifier();
            ArrayList<Type> returnType = function.getReturn_type();
            ArrayList<FuncParam> params = function.getParams();
            Block block = function.getBlock();
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


    public void  generateAST(MethodVisitor mv, Ast ast, IndexTable indexTable) throws Exception {
        // TODO: INCOMPLETE -> Manage only restraint globalVariable

        // Ast grammar: AST -> Constants Records GlobalVariables Functions
        ArrayList<Constant> cst = ast.getConstant();
        ArrayList<Record> records = ast.getRecords();


        // generate Global Variable
        ArrayList<Statement> globalVariables = ast.getGlobalVariables();
        if (globalVariables.size()>0) {
            generateGlobalVariable(mv, globalVariables, indexTable);
        }



        ArrayList<FunctionStatement> functions = ast.getFunctions();

        generateMainMethod();


    }*/


    public void generateVariableAssignment(MethodVisitor mv,AssignementStatement assignment, IndexTable indexTable) throws Exception {
        //TODO: INCOMPLETE --> manage only rightSide. Must store identifier id in IndexTable
        // Manage assignement

        // RightSide - store identifier in IndexTable
        LeftSide ls = assignment.leftSide;
        String identifier = ls.getIdentifier();
        indexTable.addIdentifier(identifier);

        // LeftSide - generate expression
        RightSideExpressions rs = (RightSideExpressions) assignment.rightSide;
        generateExpression(mv,rs.expressions,"gv", "");

        // Store the result
        int varindex = indexTable.getCurrent_index();
        mv.visitVarInsn(ISTORE, varindex);
    }


}