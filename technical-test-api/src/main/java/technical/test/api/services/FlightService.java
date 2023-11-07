package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getAllFlights() {
        return flightRepository.findAll();
    }

    public Mono<FlightRecord> saveFlight(FlightRecord flightRequest) {
        var savedFlight = FlightRecord
                .builder()
                .id(flightRequest.getId())
                .departure(flightRequest.getDeparture())
                .arrival(flightRequest.getArrival())
                .price(flightRequest.getPrice())
                .origin(String.valueOf(flightRequest.getOrigin()))
                .destination(String.valueOf(flightRequest.getDestination()))
                .image(flightRequest.getImage())
                .build();

        return  flightRepository.save(savedFlight);
    }

}
