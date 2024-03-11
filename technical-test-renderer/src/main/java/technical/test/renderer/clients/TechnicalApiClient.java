package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FiltersViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.util.UUID;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Flux<FlightViewModel> getFlights() {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Mono<FlightViewModel> createdFlight(FlightViewModel flightViewModel) {
        return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .body(Mono.just(flightViewModel), FlightViewModel.class)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }

    public Flux<AirportViewModel> getAirports() {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getAirportPath())
                .retrieve()
                .bodyToFlux(AirportViewModel.class);
    }

    public Flux<FlightViewModel> getAllFilteredFlights(FiltersViewModel filtersViewModel) {
        StringBuilder params = new StringBuilder("?");

        if (filtersViewModel.getPrice().isPresent()) {
            params.append("price=").append(filtersViewModel.getPrice().get()).append("&");
        }

        if (filtersViewModel.getOrigin().isPresent() && !filtersViewModel.getOrigin().get().isBlank()) {
            params.append("origin=").append(filtersViewModel.getOrigin().get()).append("&");
        }

        if (filtersViewModel.getDestination().isPresent() && !filtersViewModel.getDestination().get().isBlank()) {
            params.append("destination=").append(filtersViewModel.getDestination().get()).append("&");
        }

        if (filtersViewModel.getPriceSort().isPresent() && !filtersViewModel.getPriceSort().get().isBlank()) {
            params.append("priceSort=").append(filtersViewModel.getPriceSort().get()).append("&");
        }

        if (filtersViewModel.getOriginSort().isPresent() && !filtersViewModel.getOriginSort().get().isBlank()) {
            params.append("originSort=").append(filtersViewModel.getOriginSort().get()).append("&");
        }

        if (filtersViewModel.getDestinationSort().isPresent() && !filtersViewModel.getDestinationSort().get().isBlank()) {
            params.append("destinationSort=").append(filtersViewModel.getDestinationSort().get()).append("&");
        }

        params.append("page=").append(filtersViewModel.getPage()).append("&");

        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath() + "/filters" + params)
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Mono<FlightViewModel> getFlightById(UUID id) {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath() + '/' + id)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }
}
