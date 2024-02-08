package technical.test.api.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.facade.AirportFacade;
import technical.test.api.representation.AirportRepresentation;

@RestController
@RequestMapping("/airport")
@RequiredArgsConstructor
public class AirportEndpoint {
    private final AirportFacade airportFacade;

    @GetMapping
    public Flux<AirportRepresentation> getAllAirports() {
        return airportFacade.getAllAirports();
    }

    @GetMapping("/{iata}")
    public Mono<AirportRepresentation> getAirport(@PathVariable("iata") String iata) {

        return airportFacade.getAirportByIata(iata);
    }
}
