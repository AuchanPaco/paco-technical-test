package technical.test.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;
import technical.test.api.representation.FlightRepresentation;

import java.util.Optional;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightFacade flightFacade;

    @GetMapping
    public Mono<Page<FlightRepresentation>> getAllFlights(@RequestParam(value = "maximumPrice", required = false) Optional<Double> maximumPrice,
                                                          @RequestParam(value = "originIata", required = false) Optional<String> originIata,
                                                          Pageable pageable) {
        return flightFacade.getAllFlights(maximumPrice, originIata, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FlightRepresentation> createFlight(@RequestBody FlightRepresentation flightRepresentation) {
        return flightFacade.createFlight(flightRepresentation);
    }
}
