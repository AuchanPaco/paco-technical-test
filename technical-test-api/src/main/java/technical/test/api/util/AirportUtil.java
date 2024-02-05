package technical.test.api.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import technical.test.api.record.AirportRecord;

public class AirportUtil {

  private AirportUtil(){}

  public static Map<String, AirportRecord> groupAirportsByIata(final List<AirportRecord> airportRecords) {
    return Optional.of(airportRecords)
                   .map(airports -> airports.stream()
                                            .collect(Collectors.toMap(AirportRecord::getIata, Function.identity())))
                   .orElse(Map.of());
  }

}
