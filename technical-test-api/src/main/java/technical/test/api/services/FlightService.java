package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        System.err.println("finding all flights ");
        return flightRepository.findAll();
    }

    public Flux<FlightRecord> getAllFlightsFiltered(Double price, String origin, String destination, Integer page){
        return flightRepository.findAllFlightsByPriceAndOriginAndDestination(price, origin, destination, page);
    }

    public Mono<Page<FlightRecord>> getAllFlightsFilteredPageable(Double price, String origin, String destination, Pageable page){

        return flightRepository.findAllFlightsByPriceAndOriginAndDestinationPageable(price, origin, destination, page);
    }

    public Mono<FlightRecord> getFlightById(String id){
        return flightRepository.findById(id);
    }

    public Mono<FlightRecord> createFlight(FlightRecord flightRecord) {
        return flightRepository.save(flightRecord);
    }
}
