package technical.test.api.repository;

import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;

public interface FlightCriteriaRepository {

    Flux<FlightRecord> findAllFlightsByPriceAndOriginAndDestination(@Nullable Double price, @Nullable String origin, @Nullable String destination, Integer page);

    Mono<Page<FlightRecord>> findAllFlightsByPriceAndOriginAndDestinationPageable(@Nullable Double price, @Nullable String origin, @Nullable String destination, Pageable page);


}
