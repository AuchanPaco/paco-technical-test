package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightInput;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.SearchViewModel;

@Component
public class FlightFacade {

    private final FlightService flightService;
    private final AirportService airportService;

    public FlightFacade(FlightService flightService, AirportService airportService) {
        this.flightService = flightService;
        this.airportService = airportService;
    }

    public Flux<FlightViewModel> getFlights(SearchViewModel searchViewModel) {

        return this.flightService.getFlights(searchViewModel);
    }

    public Flux<AirportViewModel> getAirports() {
        return this.airportService.getAirports();
    }

    public Mono<FlightViewModel> createFlight(FlightInput flightNew) {
        return flightService.createFlight(new FlightViewModel(flightNew));
    }
}
