package technical.test.api.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PaginationTest {

  @Test
  void shouldReturnSkippedDocuments() {
    Pagination pagination = new Pagination(10, 15);
    assertEquals(135, pagination.getSkippedDocuments());
  }

}