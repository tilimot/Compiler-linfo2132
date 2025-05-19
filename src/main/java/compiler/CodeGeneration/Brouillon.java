package compiler.CodeGeneration;

import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Brouillon {
    ClassWriter cw;
    String generatedClass;

    public Brouillon(String generatedClass){
        this.cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        this.generatedClass=generatedClass;
    }


    public void generateCode() throws IOException {
        // Entry point from Top of The AST

        // CreateClass
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "[java/lang/Object", null);

        //CreateMethod
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC , "square", "([Ljava/lang/String;)V", null, null);
        //MethodVisitor mv = cw.visitMethod(ACC_PUBLIC , "square", "(I)I", null, null);
        mv.visitCode();

        // Charger la constante 3 sur la pile
        mv.visitLdcInsn(3);
        // Charger une autre constante 3 sur la pile
        mv.visitLdcInsn(3);
        // Effectuer une addition
        mv.visitInsn(IADD);

        // Stocker le résultat dans une variable locale (x)
        mv.visitVarInsn(ISTORE, 1); // Local variable 1 est utilisée pour x

        // Charger x pour l'afficher
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ILOAD, 1); // Charger x
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

        // Fin de la méthode
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();


        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
        System.out.println("Classe générée : "+this.generatedClass+".class");
    }

/*
    public void generateCode() throws IOException {
        // Entry point from Top of The AST

        // CreateClass
        cw.visit(Opcodes.V1_8, ACC_PUBLIC, this.generatedClass, null, "java/lang/Object", null);

        //CreateMethod
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();

        // Charger la constante 3 sur la pile
        mv.visitLdcInsn(3);
        // Charger une autre constante 3 sur la pile
        mv.visitLdcInsn(3);
        // Effectuer une addition
        mv.visitInsn(IADD);

        // Stocker le résultat dans une variable locale (x)
        mv.visitVarInsn(ISTORE, 1); // Local variable 1 est utilisée pour x

        // Charger x pour l'afficher
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(ILOAD, 1); // Charger x
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);

        // Fin de la méthode
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();


        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get(this.generatedClass+".class"), bytecode);
        System.out.println("Classe générée : "+this.generatedClass+".class");
    }
    */


    public static void main(String[] args) throws IOException {
        Brouillon cg = new Brouillon("MyTest");
        cg.generateCode();
    }
}
