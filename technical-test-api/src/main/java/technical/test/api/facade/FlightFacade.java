package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FiltersRepresentation;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights() {
        return flightService.getAllFlights()
                .flatMap(this.recordToRepresentation());
    }

    public Mono<FlightRepresentation> createFlight(FlightRepresentation flightRepresentation) {
        final FlightRecord flightRecord = flightMapper.convert(flightRepresentation);
        return flightService.createFlight(flightRecord)
                .flatMap(this.recordToRepresentation());
    }

    public Flux<FlightRepresentation> getAllFilteredFlights(FiltersRepresentation filtersRepresentation) {
        return flightService.getAllFilteredFlights(filtersRepresentation)
                .flatMap(this.recordToRepresentation());
    }

    public Mono<FlightRepresentation> getFlightById(UUID id) {
        return flightService.getFlightById(id)
                .flatMap(this.recordToRepresentation());
    }

    private Function<FlightRecord, Mono<FlightRepresentation>> recordToRepresentation() {
        return flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                .flatMap(tuple -> {
                    AirportRecord origin = tuple.getT1();
                    AirportRecord destination = tuple.getT2();
                    FlightRepresentation createdFlightRepresentation = flightMapper.convert(flightRecord);
                    createdFlightRepresentation.setOrigin(airportMapper.convert(origin));
                    createdFlightRepresentation.setDestination(airportMapper.convert(destination));
                    return Mono.just(createdFlightRepresentation);
                });
    }
}
