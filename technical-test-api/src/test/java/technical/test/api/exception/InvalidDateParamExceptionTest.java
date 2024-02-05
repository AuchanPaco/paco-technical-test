package technical.test.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import technical.test.api.exception.clientexception.InvalidDateParamException;

class InvalidDateParamExceptionTest {

  @Test
  void shouldReturnInvalidDateParamException() {
    InvalidDateParamException exception = new InvalidDateParamException("test");
    assertEquals("This parameter should be a valid date (format 'yyyy-MM-ddTHH-mm')", exception.getMessage());
  }

}
