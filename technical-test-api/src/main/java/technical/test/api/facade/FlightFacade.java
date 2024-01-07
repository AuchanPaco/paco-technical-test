package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlightFacade {

    private final FlightService flightService;

    private final AirportService airportService;

    private final FlightMapper flightMapper;

    private final AirportMapper airportMapper;

    public Mono<Page<FlightRepresentation>> getAllFlights(Optional<Double> maximumPrice, Optional<String> originIata, Pageable pageable) {
        return flightService.getAllFlights(maximumPrice, originIata, pageable)
                .flatMap(flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                        .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                        .flatMap(tuple -> {
                            AirportRecord origin = tuple.getT1();
                            AirportRecord destination = tuple.getT2();
                            FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                            flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                            flightRepresentation.setDestination(this.airportMapper.convert(destination));
                            return Mono.just(flightRepresentation);
                        }))
                .collectList()
                .zipWith(flightService.countAllFlights(maximumPrice, originIata))
                .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

    public Mono<FlightRepresentation> createFlight(FlightRepresentation flightRepresentation) {
        return flightService.createFlight(flightMapper.convert(flightRepresentation))
                .map(flightMapper::convert);
    }
}
