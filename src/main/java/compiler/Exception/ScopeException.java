package compiler.Exception;

public class ScopeException extends RuntimeException {
    public ScopeException(String identifier) {
      super("ScopeError: identifier '"+identifier+"' has not been declared or initialized");

    }
}
