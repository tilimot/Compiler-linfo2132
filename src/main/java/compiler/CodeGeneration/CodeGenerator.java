package compiler.CodeGeneration;
import compiler.Lexer.TokenType;
import compiler.Parser.Grammar.Expression;
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
    ArrayList<Expression> expressions; //TODO: replace by AST at the end

    public CodeGenerator(String generatedClass, ArrayList<Expression> expressions){
        this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        this.generatedClass=generatedClass;
        this.expressions=expressions; //TODO: replace by AST at the end
    }

    public void generateFileClass() throws Exception{
        // CreateClass
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "java/lang/Object", null);
        generateMainMethod();
    }

    public void generateMainMethod() throws IOException {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        generateExpression(mv);

        mv.visitVarInsn(ISTORE, 1); // store the result in var1

        // Fin de la méthode
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
        System.out.println("Classe générée : "+this.generatedClass+".class");
    }

    public void generateExpression(MethodVisitor mv){
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
            /*
            else if (val.equals("1")){
                mv.visitLdcInsn(1); //load on stack
                term +=1;
            }
            else if(val.equals("2")){
                mv.visitLdcInsn(2); //load on stack
                term +=1;
            }*/

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
        expressions.add(new Expression("1", TokenType.INTEGER,0));
        expressions.add(new Expression("+",TokenType.OPERATOR,0));
        expressions.add(new Expression("3",TokenType.INTEGER,0));
        expressions.add(new Expression("*",TokenType.OPERATOR,0));
        expressions.add(new Expression("19",TokenType.INTEGER,0));
        expressions.add(new Expression("/",TokenType.OPERATOR,0));
        expressions.add(new Expression("23",TokenType.INTEGER,0));
        expressions.add(new Expression("-",TokenType.OPERATOR,0));
        expressions.add(new Expression("2",TokenType.INTEGER,0));


        CodeGenerator cg = new CodeGenerator("MyTest2",expressions);
        cg.generateFileClass();
    }



}