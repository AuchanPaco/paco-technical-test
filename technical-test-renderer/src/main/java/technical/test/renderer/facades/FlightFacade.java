package technical.test.renderer.facades;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.dto.FlightDto;
import technical.test.renderer.dto.FlightFiltersDto;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@AllArgsConstructor
public class FlightFacade {

  private final FlightService  flightService;
  private final AirportService airportService;

  public Flux<FlightViewModel> getFlights() {
    return this.flightService.getFlights();
  }

  public Flux<AirportViewModel> getAirports() {
    return this.airportService.getAirports();
  }

  public Mono<ResponseEntity<Void>> postFlight(final FlightDto flightDto) {
    return this.flightService.postFlight(flightDto);
  }

  public Flux<FlightViewModel> getFlightsWithFilters(final FlightFiltersDto flightFilters) {
    return this.flightService.getFlightsWithFilters(flightFilters);
  }

  public Mono<FlightViewModel> getFlightById(final String selectedId) {
      return this.flightService.getFlightById(selectedId);
  }
}
