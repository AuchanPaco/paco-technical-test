package technical.test.api.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import technical.test.api.mapper.AirportMapperImpl;
import technical.test.api.mapper.FlightMapperImpl;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

class FlightFacadeTest {

  @Test
  void shouldReturnFlights() {

    FlightService  flightService  = mock(FlightService.class);
    AirportService airportService = mock(AirportService.class);

    FlightRecord flightRecord = FlightRecord.builder()
                                            .arrival(LocalDateTime.of(2024, 1, 1, 18, 0))
                                            .departure(LocalDateTime.of(2024, 1, 1, 12, 0))
                                            .origin("origin")
                                            .destination("destination")
                                            .price(123.45)
                                            .id(UUID.randomUUID())
                                            .build();

    AirportRecord airportOriginRecord = AirportRecord.builder()
                                                     .iata("origin")
                                                     .name("Origin Airport")
                                                     .country("country")
                                                     .build();

    AirportRecord airportDestinationRecord = AirportRecord.builder()
                                                          .iata("destination")
                                                          .name("Destination Airport")
                                                          .country("country")
                                                          .build();

    when(flightService.getFlights(any())).thenReturn(Flux.just(flightRecord));
    when(airportService.getAllAirports()).thenReturn(Flux.just(airportOriginRecord, airportDestinationRecord));

    StepVerifier.create(new FlightFacade(flightService, airportService, new FlightMapperImpl(), new AirportMapperImpl()).getFlights(null))
                .expectNextMatches(this::shouldFlightsMatch)
                .expectComplete()
                .verify();

  }

  private boolean shouldFlightsMatch(List<FlightRepresentation> flightRepresentations) {
    if (flightRepresentations.size() != 1) {
      return false;
    }
    FlightRepresentation flightRepresentation = flightRepresentations.get(0);
    return Objects.nonNull(flightRepresentation.getId()) &&
           Objects.equals(LocalDateTime.of(2024, 1, 1, 12, 0), flightRepresentation.getDeparture()) &&
           Objects.equals(LocalDateTime.of(2024, 1, 1, 18, 0), flightRepresentation.getArrival()) &&
           Objects.equals(123.45, flightRepresentation.getPrice()) &&
           Objects.nonNull(flightRepresentation.getOrigin()) &&
           Objects.equals("origin", flightRepresentation.getOrigin().getIata()) &&
           Objects.equals("Origin Airport", flightRepresentation.getOrigin().getName()) &&
           Objects.equals("country", flightRepresentation.getOrigin().getCountry()) &&
           Objects.nonNull(flightRepresentation.getDestination()) &&
           Objects.equals("destination", flightRepresentation.getDestination().getIata()) &&
           Objects.equals("Destination Airport", flightRepresentation.getDestination().getName()) &&
           Objects.equals("country", flightRepresentation.getDestination().getCountry()) &&
           Objects.isNull(flightRepresentation.getImage());
  }

}
