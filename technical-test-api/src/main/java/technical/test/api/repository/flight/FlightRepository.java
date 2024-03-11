package technical.test.api.repository.flight;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import technical.test.api.representation.FiltersRepresentation;
import technical.test.api.record.FlightRecord;

import java.util.UUID;

@Repository
public interface FlightRepository extends ReactiveMongoRepository<FlightRecord, UUID>, FilteredFlightRepository {

    Flux<FlightRecord> findAllByFilters(FiltersRepresentation filtersRepresentation);
}
