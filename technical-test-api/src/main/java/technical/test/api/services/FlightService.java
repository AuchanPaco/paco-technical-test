package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.flight.FlightRepository;
import technical.test.api.representation.FiltersRepresentation;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getAllFlights() {
        return flightRepository.findAll();
    }

    public Mono<FlightRecord> createFlight(FlightRecord flightRecord) {
        flightRecord.setId(UUID.randomUUID());
        return this.flightRepository.insert(flightRecord);
    }

    public Flux<FlightRecord> getAllFilteredFlights(FiltersRepresentation filtersRepresentation) {
        return flightRepository.findAllByFilters(filtersRepresentation);
    }

    public Mono<FlightRecord> getFlightById(UUID id) {
        return this.flightRepository.findById(id);
    }
}
