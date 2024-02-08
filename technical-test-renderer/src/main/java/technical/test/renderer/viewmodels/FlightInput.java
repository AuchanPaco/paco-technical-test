package technical.test.renderer.viewmodels;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FlightInput {

        private String id;

        @NotNull
        @Future(message = "departure must be in the future")
        private LocalDateTime departure;

        @NotNull
        @Future(message = "departure must be in the future")
        private LocalDateTime arrival;

        @NotNull
        @Min(message = "price must can't be zero", value = 0)
        private double price;

        @NotNull
        @NotBlank
        private String origin;


        @NotNull
        @NotBlank
        private String destination;


        @NotNull
        @NotBlank
        private String image;

}
