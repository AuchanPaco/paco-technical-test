package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class FlightFacade {
    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights() {
        return getFlightRepresentations(flightService.getAllFlights());

    }


    public Flux<FlightRepresentation> getFilteredFlights(Double price, String origin, String destination, Integer page){
        return getFlightRepresentations(flightService.getAllFlightsFiltered(price, origin, destination , page));
    }

    public Mono<FlightRepresentation> getFlight(String flightId){
        Mono<FlightRecord> flightRecordMono = flightService.getFlightById(flightId);
        return flightRecordMono.flatMap( getRepresentation() );
    }

    public Mono<FlightRepresentation> createFlight(Mono<FlightRepresentation> flightRepresentationMono) {

        return flightRepresentationMono.flatMap(flightRepresentation -> airportService.getAirportOrThrow(this.airportMapper.convert(flightRepresentation.getOrigin()))
                .zipWith(airportService.getAirportOrThrow(this.airportMapper.convert(flightRepresentation.getDestination())))
                .flatMap(tuple->{
                    AirportRecord origin = tuple.getT1();
                    AirportRecord destination = tuple.getT2();
                    FlightRecord flightRecord = this.flightMapper.convert(flightRepresentation);
                    flightRecord.setOrigin(origin.getIata());
                    flightRecord.setDestination(destination.getIata());
                    return flightService.createFlight(flightRecord)
                            .flatMap(flight->{
                                FlightRepresentation flightResponse = this.flightMapper.convert(flight);
                                flightResponse.setOrigin(this.airportMapper.convert(origin));
                                flightResponse.setDestination(this.airportMapper.convert(destination));
                                return Mono.just(flightResponse);
                            });
                }) ) ;

    }

    private Flux<FlightRepresentation> getFlightRepresentations(Flux<FlightRecord> flights){
        return flights.flatMap(getRepresentation());

    }

    private Function<FlightRecord, Mono<? extends FlightRepresentation>> getRepresentation() {
        return flightRecord -> airportService.findByIataCode(flightRecord.getOrigin())
                .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                .flatMap(tuple -> {
                    AirportRecord origin = tuple.getT1();
                    AirportRecord destination = tuple.getT2();
                    FlightRepresentation flightRepresentation = this.flightMapper.convert(flightRecord);
                    flightRepresentation.setOrigin(this.airportMapper.convert(origin));
                    flightRepresentation.setDestination(this.airportMapper.convert(destination));
                    return Mono.just(flightRepresentation);
                });
    }

}
