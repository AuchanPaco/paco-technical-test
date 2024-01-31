package technical.test.api.representation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRepresentation {
    private String id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private AirportRepresentation origin;
    private AirportRepresentation destination;
    private String image;
}
