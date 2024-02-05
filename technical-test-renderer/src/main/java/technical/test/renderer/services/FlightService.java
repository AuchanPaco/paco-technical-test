package technical.test.renderer.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.dto.FlightDto;
import technical.test.renderer.dto.FlightFiltersDto;
import technical.test.renderer.tools.FlightUriTools;
import technical.test.renderer.viewmodels.FlightViewModel;

@Service
@AllArgsConstructor
public class FlightService {

  private final WebClient flightApiWebClient;

  public Flux<FlightViewModel> getFlights() {
    return this.flightApiWebClient.get()
                                  .retrieve()
                                  .bodyToFlux(FlightViewModel.class);
  }

  public Mono<ResponseEntity<Void>> postFlight(final FlightDto flightDto) {
    return this.flightApiWebClient.post()
                                  .bodyValue(flightDto)
                                  .retrieve()
                                  .toBodilessEntity();
  }

  public Flux<FlightViewModel> getFlightsWithFilters(final FlightFiltersDto flightFilters) {
    return this.flightApiWebClient.get()
                                  .uri(uriBuilder -> FlightUriTools.generateFlightUriWithFilters(uriBuilder, flightFilters))
                                  .retrieve()
                                  .bodyToFlux(FlightViewModel.class);
  }

  public Mono<FlightViewModel> getFlightById(final String selectedId) {
    return this.flightApiWebClient.get()
                                  .uri(uriBuilder -> FlightUriTools.generateFlightUriWithId(uriBuilder, selectedId))
                                  .retrieve()
                                  .bodyToMono(FlightViewModel.class);
  }
}
