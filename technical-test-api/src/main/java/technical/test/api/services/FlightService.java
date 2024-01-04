package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getAllFlights(Optional<Double> maximumPrice, Optional<String> originIata, Pageable pageable) {
        if (maximumPrice.isPresent() && originIata.isPresent()) {
            return flightRepository.findAllByPriceLessThanAndOrigin(maximumPrice.get(), originIata.get(), pageable);
        }
        if (maximumPrice.isPresent()) {
            return flightRepository.findAllByPriceLessThan(maximumPrice.get(), pageable);
        }
        if (originIata.isPresent()) {
            return flightRepository.findAllByOrigin(originIata.get(), pageable);
        }
        return flightRepository.findAllBy(pageable);
    }

    public Mono<Long> countAllFlights(Optional<Double> maximumPrice, Optional<String> originIata) {
        if (maximumPrice.isPresent() && originIata.isPresent()) {
            return flightRepository.countByPriceLessThanAndOrigin(maximumPrice.get(), originIata.get());
        }
        if (maximumPrice.isPresent()) {
            return flightRepository.countByPriceLessThan(maximumPrice.get());
        }
        if (originIata.isPresent()) {
            return flightRepository.countByOrigin(originIata.get());
        }
        return flightRepository.count();
    }

    public Mono<FlightRecord> createFlight(FlightRecord flightRecord) {
        return flightRepository.save(flightRecord);
    }
}
