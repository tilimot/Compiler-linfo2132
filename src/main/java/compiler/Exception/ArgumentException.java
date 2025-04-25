package compiler.Exception;

public class ArgumentException extends RuntimeException {
  public ArgumentException() {
    super("ArgumentError: Wrong arguments");
    System.exit(1);
  }
}
