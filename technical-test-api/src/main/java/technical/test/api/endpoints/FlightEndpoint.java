package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.request.FlightRequest;
import technical.test.api.services.FlightService;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;
    private final FlightService flightService;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights() {
        return flightFacade.getAllFlights();
    }

    @PostMapping
    public Mono<FlightRecord> saveFlight(@RequestBody FlightRecord flightRecord) {
        return flightService.saveFlight(flightRecord);
    }
}
