package technical.test.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import technical.test.api.facade.AirportFacade;
import technical.test.api.representation.AirportRepresentation;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportFacade airportFacade;

    @GetMapping
    public Flux<AirportRepresentation> getAllAirports() {
        return airportFacade.getAllAirports();
    }
}
