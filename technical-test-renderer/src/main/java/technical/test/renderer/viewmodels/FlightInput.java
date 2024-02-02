package technical.test.renderer.viewmodels;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FlightInput {


        private String id;
        private LocalDateTime departure;
        private LocalDateTime arrival;
        private double price;
        private String origin;
        private String destination;
        private String image;

}
