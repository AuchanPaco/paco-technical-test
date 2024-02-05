package technical.test.api.exception.clientexception;

import java.util.List;
import technical.test.api.exception.ClientException;

public class InvalidParamException extends ClientException {

  public InvalidParamException(final String element, final String message) {
    super(element, message);
  }

  public InvalidParamException(final List<String> elements, final String message) {
    super(String.join(",", elements), message);
  }
}
