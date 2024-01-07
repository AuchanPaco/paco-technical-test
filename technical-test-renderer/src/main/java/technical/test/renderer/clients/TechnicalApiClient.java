package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightResponse;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Mono<FlightResponse> getFlights(Integer page, Integer size) {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath() + "?page=" + page + "&size=" + size)
                .retrieve()
                .bodyToMono(FlightResponse.class);
    }

    public Mono<FlightViewModel> createFlights(FlightViewModel newFlight) {
        return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .body(newFlight, FlightViewModel.class)
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
}
