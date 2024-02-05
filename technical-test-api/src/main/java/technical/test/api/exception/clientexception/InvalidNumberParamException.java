package technical.test.api.exception.clientexception;

public class InvalidNumberParamException extends InvalidParamException{

  public InvalidNumberParamException(final String element) {
    super(element, "This parameter should be a number");
  }
}
