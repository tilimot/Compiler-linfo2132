package compiler.CodeGeneration;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.*;
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
    AssignementStatement assignment; //TODO: replace by AST at the end
    IndexTable indexTable;


    public CodeGenerator(String generatedClass,AssignementStatement assignment){
        this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        this.generatedClass=generatedClass;
        this.assignment=assignment; //TODO: replace by AST at the end
        this.indexTable = new IndexTable(null);

    }

    public void generateFileClass() throws Exception{
        // CreateClass
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "java/lang/Object", null);
        generateMainMethod();
    }

    public void generateMainMethod() throws IOException {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        //generateExpression(mv);
        generateAssignment(mv,this.assignment, this.indexTable);

        mv.visitVarInsn(ISTORE, 1); // store the result in var1

        // Fin de la méthode
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
        System.out.println("Classe générée : "+this.generatedClass+".class");
    }

    public void generateAssignment(MethodVisitor mv,AssignementStatement assignment ,IndexTable indexTable){
        // Manage assignement
    }

    public void generateExpression(MethodVisitor mv, ArrayList<Expression> expressions){
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

    //TODO: Need to delete it at the end
    public static void main(String[] args) throws Exception {
        ArrayList<Expression> expressions = new ArrayList<>();
        expressions.add(new Expression("hello", TokenType.STRINGS,0));
        expressions.add(new Expression("+",TokenType.OPERATOR,0));
        expressions.add(new Expression(" world",TokenType.STRINGS,0));
        /*
        expressions.add(new Expression("1.3", TokenType.FLOAT,0));
        expressions.add(new Expression("+",TokenType.OPERATOR,0));
        expressions.add(new Expression("3",TokenType.INTEGER,0));
        expressions.add(new Expression("*",TokenType.OPERATOR,0));
        expressions.add(new Expression("false",TokenType.BOOLEAN,0));
        expressions.add(new Expression("/",TokenType.OPERATOR,0));
        expressions.add(new Expression("hello",TokenType.STRINGS,0));
        expressions.add(new Expression("-",TokenType.OPERATOR,0));
        expressions.add(new Expression("2",TokenType.INTEGER,0));
         */

        //CodeGenerator cg = new CodeGenerator("MyTest2",expressions);
        //cg.generateFileClass();
    }



}