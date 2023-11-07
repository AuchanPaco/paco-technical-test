package technical.test.api.request;

import lombok.Builder;
import lombok.Data;
import technical.test.api.representation.AirportRepresentation;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightRequest {

    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private AirportRepresentation origin;
    private AirportRepresentation destination;
    private String image;
}
