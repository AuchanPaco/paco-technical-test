package technical.test.renderer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import technical.test.renderer.viewmodels.AirportViewModel;

@Service
@AllArgsConstructor
public class AirportService {

  private final WebClient airportApiWebClient;

  public Flux<AirportViewModel> getAirports() {
    return this.airportApiWebClient.get()
                                   .retrieve()
                                   .bodyToFlux(AirportViewModel.class);
  }

}
