package technical.test.api.util;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import technical.test.api.record.AirportRecord;

class AirportUtilTest {

  @Test
  void shouldReturnAirportsGroupedByIata() {
    AirportRecord airportRecord = AirportRecord.builder()
                                               .iata("iata")
                                               .country("country")
                                               .name("name")
                                               .build();

    AirportRecord airportRecord2 = AirportRecord.builder()
                                               .iata("iata2")
                                               .country("country2")
                                               .name("name2")
                                               .build();

    Map<String, AirportRecord> mapAirports = AirportUtil.groupAirportsByIata(List.of(airportRecord, airportRecord2));
    assertTrue(mapAirports.containsKey("iata"));
    assertTrue(mapAirports.containsKey("iata2"));
    assertSame(airportRecord, mapAirports.get("iata"));
    assertSame(airportRecord2, mapAirports.get("iata2"));

  }

}
