package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightRepresentation;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightEndpoint {
    private final FlightFacade flightFacade;

    @GetMapping
    public Flux<FlightRepresentation> getAllFlights() {
        return flightFacade.getAllFlights();
    }

    @GetMapping("{flightId}")
    public Mono<FlightRepresentation> getFlight(@PathVariable String flightId) {
        return flightFacade.getFlight(flightId) ;
    }

    @GetMapping("/search")
    public Flux<FlightRepresentation> getFlightsFiltered(
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "origin", required = false) String origin,
            @RequestParam(name = "destination", required = false) String destination,
            @RequestParam(name = "page", required = false) Integer page
    ) {
        return flightFacade.getFilteredFlights(price, origin, destination, page);
    }

    @PostMapping
    public Mono<FlightRepresentation> createFlight(
            @RequestBody Mono<FlightRepresentation> flightRepresentationMono
    ) {
        return flightFacade.createFlight(flightRepresentationMono);
    }


}
