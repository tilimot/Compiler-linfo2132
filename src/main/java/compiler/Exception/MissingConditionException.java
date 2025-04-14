package compiler.Exception;

public class MissingConditionException extends Exception {
    public MissingConditionException() {
        super("MissingConditionError: Must attribute a condition");
    }
}
