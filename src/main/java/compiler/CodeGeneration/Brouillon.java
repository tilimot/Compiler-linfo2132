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

/*
    public static void main(String[] args) throws IOException {
        Brouillon cg = new Brouillon("MyTest");
        cg.generateCode();
    }*/



    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "MyClass", null, "java/lang/Object", null);

        // Générer le constructeur par défaut
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // Générer la méthode myMethod(int a, int b)
        mv = cw.visitMethod(ACC_PUBLIC, "myMethod", "(II)V", null, null);
        mv.visitCode();

        Label elseLabel = new Label(); // Label pour le début du bloc else
        Label endIfElseLabel = new Label(); // Label pour la fin de la structure if/else

        // Charger 'a' (local variable 1) et 'b' (local variable 2) sur la pile
        mv.visitVarInsn(ILOAD, 1); // Charge 'a' (int)
        mv.visitVarInsn(ILOAD, 2); // Charge 'b' (int)

        // Comparer a et b
        // IF_ICMPLE: Si 'a' est inférieur ou égal à 'b', sauter à elseLabel
        // Cela signifie que si a > b est FAUX, on exécute le bloc else
        mv.visitJumpInsn(IF_ICMPLE, elseLabel);

        // --- Bloc IF (a > b) ---
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("a est plus grand que b");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // Sauter à la fin de la structure if/else pour ignorer le bloc else
        mv.visitJumpInsn(GOTO, endIfElseLabel);

        // --- Bloc ELSE (a <= b) ---
        mv.visitLabel(elseLabel); // Marquer le début du bloc else
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("a n'est pas plus grand que b");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // --- Fin de la structure IF/ELSE ---
        mv.visitLabel(endIfElseLabel); // Marquer la fin de la structure

        mv.visitInsn(RETURN); // Retour de la méthode
        mv.visitMaxs(2, 3); // Max stack size, max local variables (pour la méthode myMethod)
        mv.visitEnd();

        cw.visitEnd();

        // Écrire le fichier .class
        byte[] bytecode = cw.toByteArray();
        java.nio.file.Files.write(java.nio.file.Paths.get("Brouillon"+".class"), bytecode);

        System.out.println("MyClass.class généré avec succès.");
    }
}
