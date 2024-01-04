package technical.test.api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;

import java.util.UUID;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<FlightRecord, UUID> {

    Flux<FlightRecord> findAllBy(Pageable pageable);

    Flux<FlightRecord> findAllByPriceLessThan(Double maximumPrice, Pageable pageable);

    Flux<FlightRecord> findAllByOrigin(String originIata, Pageable pageable);

    Flux<FlightRecord> findAllByPriceLessThanAndOrigin(Double maximumPrice, String originIata, Pageable pageable);

    Mono<Long> countByPriceLessThan(Double price);

    Mono<Long> countByOrigin(String originIata);

    Mono<Long> countByPriceLessThanAndOrigin(Double price, String originIata);
}
