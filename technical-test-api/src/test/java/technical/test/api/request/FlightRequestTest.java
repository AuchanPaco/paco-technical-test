package technical.test.api.request;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import technical.test.api.exception.clientexception.InvalidDateParamException;
import technical.test.api.exception.clientexception.InvalidNumberParamException;
import technical.test.api.exception.clientexception.InvalidParamException;

class FlightRequestTest {

  @Test
  void shouldThrowInvalidNumberExceptionWithInvalidMinPrice() {
    assertThrows(InvalidNumberParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("minPrice", "abc")));
  }

  @Test
  void shouldThrowInvalidNumberExceptionWithInvalidMaxPrice() {
    assertThrows(InvalidNumberParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("maxPrice", "abc")));
  }

  @Test
  void shouldThrowInvalidParamExceptionWithMinPriceGreaterThanMaxPrice() {
    assertThrows(InvalidParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("minPrice", "200", "maxPrice", "10")));
  }

  @Test
  void shouldThrowInvalidDateParamExceptionWithInvalidMinDate() {
    assertThrows(InvalidDateParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("minDate", "abc")));
  }

  @Test
  void shouldThrowInvalidDateParamExceptionWithInvalidMaxDate() {
    assertThrows(InvalidDateParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("maxDate", "abc")));
  }

  @Test
  void shouldThrowInvalidParamExceptionWithMinDateGreaterThanMaxDate() {
    assertThrows(InvalidParamException.class, () -> FlightRequest.extractGetFlightCriterias(Map.of("minDate","2024-01-02T00:00",
                                                                                                       "maxDate","2024-01-01T00:00")));
  }

  @Test
  void shouldExtractFullFlightCriterias() {
    final List<Criteria> criteriaList = FlightRequest.extractGetFlightCriterias(Map.of("minPrice", "100",
                                                                                       "maxPrice", "200",
                                                                                       "origin", "originTest",
                                                                                       "destination", "destinationTest",
                                                                                       "minDate", "2024-01-01T00:00",
                                                                                       "maxDate", "2024-01-02T00:00"));

    final Criteria expectedMinPriceCriteria    = Criteria.where("price").gte(100D);
    final Criteria expectedMaxPriceCriteria    = Criteria.where("price").lte(200D);
    final Criteria expectedOriginCriteria      = Criteria.where("origin").is("originTest");
    final Criteria expectedDestinationCriteria = Criteria.where("destination").is("destinationTest");
    final Criteria expectedMinDateCriteria = Criteria.where("departure").gte(LocalDateTime.of(2024,1,1,0,0));
    final Criteria expectedMaxDateCriteria = Criteria.where("departure").lte(LocalDateTime.of(2024,1,2,0,0));

    assertEquals(6, criteriaList.size());
    assertEquals(expectedMinPriceCriteria, criteriaList.get(0));
    assertEquals(expectedMaxPriceCriteria, criteriaList.get(1));
    assertEquals(expectedOriginCriteria, criteriaList.get(2));
    assertEquals(expectedDestinationCriteria, criteriaList.get(3));
    assertEquals(expectedMinDateCriteria, criteriaList.get(4));
    assertEquals(expectedMaxDateCriteria, criteriaList.get(5));
  }

  @Test
  void shouldNotExtractPaginationWithEmptyParams() {
    assertNull(FlightRequest.extractPagination(Map.of()));
  }

  @Test
  void shouldNotExtractPaginationWithDefaultPagination() {
    assertNull(FlightRequest.extractPagination(Map.of("page", "1", "size", "0")));
  }

  @Test
  void shouldThrowInvalidNumberExceptionWithInvalidPage() {
    assertThrows(InvalidNumberParamException.class, () -> FlightRequest.extractPagination(Map.of("page", "abc")));
  }

  @Test
  void shouldThrowInvalidNumberExceptionWithInvalidSize() {
    assertThrows(InvalidNumberParamException.class, () -> FlightRequest.extractPagination(Map.of("size", "abc")));
  }

  @Test
  void shouldExtractPagination() {
    final Pagination pagination = FlightRequest.extractPagination(Map.of("page", "12",
                                                                         "size", "10"));
    assertNotNull(pagination);
    assertEquals(12, pagination.page());
    assertEquals(10, pagination.size());
  }

}