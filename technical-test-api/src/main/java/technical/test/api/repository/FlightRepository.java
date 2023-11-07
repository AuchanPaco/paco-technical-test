package technical.test.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.request.FlightRequest;

import java.util.UUID;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<FlightRecord, UUID> {

    Mono<FlightRecord> save(FlightRecord flightRecord);
}
