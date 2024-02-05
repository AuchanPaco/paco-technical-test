package technical.test.api.exception.clientexception;

public class InvalidDateParamException extends InvalidParamException{

  public InvalidDateParamException(final String element) {
    super(element, "This parameter should be a valid date (format 'yyyy-MM-ddTHH-mm')");
  }
}
