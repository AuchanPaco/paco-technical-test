package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
public class FlightFacade {

    private final FlightService flightService;
    private final AirportService airportService;

    public FlightFacade(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public Flux<FlightViewModel> getFlights() {
        return this.flightService.getFlights();
    }

    public Flux<AirportViewModel> getAirports() {
        return this.airportService.getAirports();
    }
}
