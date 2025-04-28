package compiler.Exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(" variable "+message+ " has already been assigned");
    }
}
