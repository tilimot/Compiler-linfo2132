package compiler.Exception;

public class OperatorException extends Exception {
    public OperatorException(String leftSide, String rightSide)
    {
        super("OperatorError: Trying to operate "+leftSide+" with "+rightSide);
    }
}
