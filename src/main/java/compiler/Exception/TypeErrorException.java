package compiler.Exception;

public class TypeErrorException extends Exception{
    String errorMessage;

    public TypeErrorException(String errorMessage){
        super(errorMessage);
    }
}
