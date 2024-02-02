package technical.test.renderer.viewmodels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FlightViewModel {
    private String id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String image;
    private AirportViewModel origin;
    private AirportViewModel destination;


    public FlightViewModel(FlightInput input){
        this.id = input.getId();
        departure = input.getDeparture();
        arrival = input.getArrival();
        price = input.getPrice();
        image = input.getImage();

        AirportViewModel origin = new AirportViewModel();
        origin.setIata(input.getOrigin());
        AirportViewModel destination = new AirportViewModel();
        destination.setIata(input.getDestination());

        this.origin = origin;
        this.destination = destination;
    }
}
