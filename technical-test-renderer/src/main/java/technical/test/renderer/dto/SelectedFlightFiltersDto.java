package technical.test.renderer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectedFlightFiltersDto extends FlightFiltersDto {
  private String selectedId;
}
