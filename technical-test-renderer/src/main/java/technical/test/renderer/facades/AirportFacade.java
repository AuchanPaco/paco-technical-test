package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.viewmodels.AirportViewModel;

@Component
public class AirportFacade {

    private final AirportService airportService;

    public AirportFacade(AirportService airportService) {
        this.airportService = airportService;
    }

    public Flux<AirportViewModel> getAirports() {
        return this.airportService.getAirports();
    }
}
