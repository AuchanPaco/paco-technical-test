package technical.test.renderer.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightFiltersDto {

  private Double        minPrice;
  private Double        maxPrice;
  private String        origin;
  private String        destination;
  private LocalDateTime minDate;
  private LocalDateTime maxDate;

}
