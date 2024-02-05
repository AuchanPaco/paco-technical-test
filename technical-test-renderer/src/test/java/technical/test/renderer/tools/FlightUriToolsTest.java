package technical.test.renderer.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.DefaultUriBuilderFactory;
import technical.test.renderer.dto.FlightFiltersDto;

class FlightUriToolsTest {

  @Test
  void shouldReturnMinimalUri() {
    final URI uri = FlightUriTools.generateFlightUriWithFilters(new DefaultUriBuilderFactory().builder(), new FlightFiltersDto());

    assertEquals("", uri.toString());
  }

  @Test
  void shouldReturnUri() {
    FlightFiltersDto flightFiltersDto = new FlightFiltersDto(10D, 20D, "origin", "destination", LocalDateTime.of(2024,1,1,0,0), LocalDateTime.of(2024,1,2,0,0));

    final URI uri = FlightUriTools.generateFlightUriWithFilters(new DefaultUriBuilderFactory().builder(), flightFiltersDto);

    assertEquals("?origin=origin&destination=destination&minPrice=10&maxPrice=20&minDate=2024-01-01T00:00&maxDate=2024-01-02T00:00", uri.toString());
  }

  @Test
  void shouldReturnUriWithId() {
    final URI uri = FlightUriTools.generateFlightUriWithId(new DefaultUriBuilderFactory().builder(), "selectedId");
    assertEquals("/selectedId", uri.toString());
  }

}
