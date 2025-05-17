package compiler.Exception;

public class InvalidParametersException extends RuntimeException {
  public InvalidParametersException(String value, String type) {
    super("InvalidParametersError: " + value + " is not a valid parameter for " + type);
  }
}