package technical.test.renderer.viewmodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightResponse {

    private List<FlightViewModel> content;

    private Integer totalPages;

    private Integer totalElements;

    private Integer size;

    private Integer number;
}
