package technical.test.api.representation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import technical.test.api.validation.BasicInfo;

import java.time.LocalDateTime;

@Data
public class FlightRepresentation {

    private String id;

    @NotNull
    @Future(message = "departure must be in the future", groups = BasicInfo.class)
    private LocalDateTime departure;

    @NotNull
    @Future(message = "departure must be in the future",  groups = BasicInfo.class)
    private LocalDateTime arrival;

    @NotNull
    @Min(message = "price must can't be zero", value = 0,  groups = BasicInfo.class)
    private double price;

    @Valid
    @NotNull( groups = BasicInfo.class)
    private AirportRepresentation origin;

    @Valid
    @NotNull( groups = BasicInfo.class)
    private AirportRepresentation destination;

    @NotNull
    @NotBlank
    private String image;
}
