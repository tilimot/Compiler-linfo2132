package compiler.Exception;

public class RecordException extends Exception {
  public RecordException()
  {
    super("RecordError");
    System.exit(1);

  }
}
