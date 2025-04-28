package compiler.Exception;

public class FinalException extends RuntimeException {
    public FinalException(String message) {
        super("Final variable "+message+ " has already been assigned");
    }
}
