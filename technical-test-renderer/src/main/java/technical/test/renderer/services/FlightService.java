package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.SearchViewModel;

@Service
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public FlightService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Flux<FlightViewModel> getFlights(SearchViewModel searchViewModel) {
        if (searchViewModel.getFavoriteFlight()!=null && !searchViewModel.getFavoriteFlight().isBlank()){
            return this.technicalApiClient.getFlight(searchViewModel.getFavoriteFlight());
        }
        return this.technicalApiClient.getFlights(searchViewModel);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {


        return technicalApiClient.createFlight(flightViewModel);
    }
}
