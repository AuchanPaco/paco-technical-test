package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.AirportViewModel;

@Service
public class AirportService {
    private final TechnicalApiClient technicalApiClient;

    public AirportService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Flux<AirportViewModel> getAirports() {
        return this.technicalApiClient.getAirports();
    }
}
