package technical.test.renderer.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {

  private LocalDateTime departure;
  private LocalDateTime arrival;
  private double        price;
  private String        origin;
  private String        destination;
  private String        image;
}
