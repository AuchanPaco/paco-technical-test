package technical.test.renderer.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.FlightResponse;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@RequiredArgsConstructor
public class FlightFacade {

    private final FlightService flightService;

    public Mono<FlightResponse> getFlights(Integer page, Integer size) {
        return this.flightService.getFlights(page, size);
    }

    public Mono<FlightViewModel> createFlights(FlightViewModel newFlight) {
        return this.flightService.createFlights(newFlight);
    }
}
