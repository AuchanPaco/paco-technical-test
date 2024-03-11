package technical.test.api.repository.flight;

import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FiltersRepresentation;

public interface FilteredFlightRepository {

    int PAGE_SIZE = 6;

    Flux<FlightRecord> findAllByFilters(FiltersRepresentation filtersRepresentation);
}
