package technical.test.api.record;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "flight")
public class FlightRecord {
    @Id
    private String id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String origin;
    private String destination;
    private String image;
}
