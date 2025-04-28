package compiler.Exception;

public class ReturnException extends RuntimeException {
    public ReturnException(String func_type, String return_type) {
        super("ReturnError: Should return "+func_type+" but return a "+return_type);

    }
}
