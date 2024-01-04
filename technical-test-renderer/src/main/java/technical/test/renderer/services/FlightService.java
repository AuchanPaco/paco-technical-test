package technical.test.renderer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightResponse;
import technical.test.renderer.viewmodels.FlightViewModel;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final TechnicalApiClient technicalApiClient;

    public Mono<FlightResponse> getFlights(Integer page, Integer size) {
        return this.technicalApiClient.getFlights(page, size);
    }

    public Mono<FlightViewModel> createFlights(FlightViewModel newFlight) {
        return this.technicalApiClient.createFlights(newFlight);
    }
}
