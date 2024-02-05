package technical.test.renderer.tools;

import java.net.URI;
import java.util.Optional;
import org.springframework.web.util.UriBuilder;
import technical.test.renderer.dto.FlightFiltersDto;

public class FlightUriTools {

  private FlightUriTools() {
  }

  public static URI generateFlightUriWithFilters(UriBuilder uriBuilder, final FlightFiltersDto flightFilters) {
    return uriBuilder.queryParamIfPresent("origin", Optional.ofNullable(flightFilters.getOrigin()))
                     .queryParamIfPresent("destination", Optional.ofNullable(flightFilters.getDestination()))
                     .queryParamIfPresent("minPrice", Optional.ofNullable(flightFilters.getMinPrice())
                                                              .map(Double::intValue))
                     .queryParamIfPresent("maxPrice", Optional.ofNullable(flightFilters.getMaxPrice())
                                                              .map(Double::intValue))
                     .queryParamIfPresent("minDate", Optional.ofNullable(flightFilters.getMinDate()))
                     .queryParamIfPresent("maxDate", Optional.ofNullable(flightFilters.getMaxDate()))
                     .build();
  }

  public static URI generateFlightUriWithId(final UriBuilder uriBuilder, final String selectedId) {
    return uriBuilder.pathSegment(selectedId)
                     .build();
  }
}
