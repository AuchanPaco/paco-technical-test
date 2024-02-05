package technical.test.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import technical.test.api.exception.clientexception.InvalidNumberParamException;

class InvalidNumberParamExceptionTest {

  @Test
  void shouldReturnInvalidNumberParamException() {
    InvalidNumberParamException exception = new InvalidNumberParamException("test");
    assertEquals("This parameter should be a number", exception.getMessage());
  }


}
