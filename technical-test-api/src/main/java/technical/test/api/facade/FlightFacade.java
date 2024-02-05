package technical.test.api.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.constants.RequestParameterConstants;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.AirportRepresentation;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;
import technical.test.api.util.AirportUtil;

@Component
@RequiredArgsConstructor
public class FlightFacade {

  private final FlightService  flightService;
  private final AirportService airportService;
  private final FlightMapper   flightMapper;
  private final AirportMapper  airportMapper;

  public Mono<FlightRecord> postFlight(final FlightRecord flightRecord) {
    // We have to add an uuid, as there is no auto incrementation
    flightRecord.setId(UUID.randomUUID());
    return this.flightService.postFlight(flightRecord);
  }

  public Mono<List<FlightRepresentation>> getFlights(final Map<String, String> queryParams) {
    return this.flightService.getFlights(queryParams).collectList()
                             .zipWith(this.airportService.getAllAirports().collectList())
                             .map(tuple -> {
                               Map<String, AirportRecord> mapAirportByIata = AirportUtil.groupAirportsByIata(tuple.getT2());
                               return tuple.getT1()
                                           .stream()
                                           .map(flight -> this.convertFlightRecordToFlightRepresentation(flight, mapAirportByIata))
                                           .toList();
                             })
                             .filter(flights -> !CollectionUtils.isEmpty(flights));
  }

  public Mono<FlightRepresentation> getFlightById(final String flightId) {
    return this.flightService.getFlightById(flightId)
                             .zipWith(this.airportService.getAllAirports().collectList())
                             .map(tuple -> this.convertFlightRecordToFlightRepresentation(tuple.getT1(), AirportUtil.groupAirportsByIata(tuple.getT2())));
  }

  public Mono<List<AirportRepresentation>> getAirports() {
    return this.airportService.getAllAirports()
                              .map(this.airportMapper::convert)
                              .collectList();
  }

  private FlightRepresentation convertFlightRecordToFlightRepresentation(final FlightRecord flightRecord, final Map<String, AirportRecord> mapAirportByIata) {
    FlightRepresentation       flightRepresentation = this.flightMapper.convert(flightRecord);
    flightRepresentation.setOrigin(this.airportMapper.convert(mapAirportByIata.get(flightRecord.getOrigin())));
    flightRepresentation.setDestination(this.airportMapper.convert(mapAirportByIata.get(flightRecord.getDestination())));
    return flightRepresentation;
  }


}
