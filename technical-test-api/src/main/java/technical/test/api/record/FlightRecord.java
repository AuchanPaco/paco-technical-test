package technical.test.api.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import technical.test.api.request.FlightRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Document(collection = "flight")
public class FlightRecord {
    @Id
    private UUID id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String origin;
    private String destination;
    private String image;
}
