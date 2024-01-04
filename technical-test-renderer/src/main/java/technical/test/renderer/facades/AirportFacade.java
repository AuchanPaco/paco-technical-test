package technical.test.renderer.facades;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.viewmodels.AirportViewModel;

@Component
@RequiredArgsConstructor
public class AirportFacade {

    private final AirportService airportService;

    public Flux<AirportViewModel> getAirports() {
        return this.airportService.getAirports();
    }
}
