package compiler.Exception;

public class FloatException extends RuntimeException {
    public FloatException(String message) {
        super("FloatError: "+message);
    }
}
