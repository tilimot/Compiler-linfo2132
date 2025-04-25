package compiler.Exception;

public class TypeException extends Exception{

    public TypeException(){
        super("TypeError: Trying to assign a value of incompatible type");
        //System.exit(1);


    }
}
