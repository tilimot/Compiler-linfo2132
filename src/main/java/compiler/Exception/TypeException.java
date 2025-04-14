package compiler.Exception;

public class TypeException extends Exception{

    public TypeException(String leftSidePart, String rightSidePart){
        super("TypeError: Trying to assign a "+rightSidePart+ " to a "+ leftSidePart);

    }
}
