package technical.test.api.services;

import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.exception.ClientException;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.request.FlightRequest;
import technical.test.api.request.Pagination;

@Service
@RequiredArgsConstructor
public class FlightService {

  private final FlightRepository flightRepository;

  private static final Sort DEFAULT_FLIGHT_SORT = Sort.by(Order.asc("departure"));

  public Mono<FlightRecord> postFlight(final FlightRecord flightRecord) {
    return this.flightRepository.insert(flightRecord);
  }

  public Flux<FlightRecord> getFlights(final Map<String, String> queryParams) {
    List<Criteria> criterias = null;
    try {
      criterias = FlightRequest.extractGetFlightCriterias(queryParams);
    } catch (ClientException clientException) {
      return Flux.error(clientException);
    }

    Pagination pagination = null;
    try {
      pagination = FlightRequest.extractPagination(queryParams);
    } catch (ClientException clientException) {
      return Flux.error(clientException);
    }

    return !criterias.isEmpty() || Objects.nonNull(pagination) ?
           this.flightRepository.getFlightByCriterias(criterias, DEFAULT_FLIGHT_SORT, pagination) :
           this.flightRepository.findAll(DEFAULT_FLIGHT_SORT);
  }

  public Mono<FlightRecord> getFlightById(final String flightId) {
    return this.flightRepository.findById(UUID.fromString(flightId));
  }
}
