package technical.test.api.repository;

import jakarta.annotation.Nullable;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;

public interface FlightCriteriaRepository {

    Flux<FlightRecord> findAllFlightsByPriceAndOriginAndDestination(@Nullable Double price, @Nullable String origin, @Nullable String destination, int page);


}
