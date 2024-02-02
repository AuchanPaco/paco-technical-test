package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.SearchViewModel;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Flux<FlightViewModel> getFlights(SearchViewModel searchViewModel) {

        WebClient webClient = WebClient.create(technicalApiProperties.getUrl());
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/flight/search")
                                .queryParam("origin", searchViewModel.getOrigin())
                                .queryParam("destination", searchViewModel.getDestination())
                                .queryParam("price", searchViewModel.getPrice())
                                .build()
                )
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Flux<AirportViewModel> getAirports() {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getAirportPath())
                .retrieve()
                .bodyToFlux(AirportViewModel.class);
    }

    public Mono<FlightViewModel> createFlight(FlightViewModel flightViewModel) {
        return webClient
                .post()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(flightViewModel), FlightViewModel.class)
                .retrieve()
                .bodyToMono(FlightViewModel.class)
                .doOnError(e -> log.error("Error occurred: ", e))
                .doOnSuccess(response -> log.info("Request successful: {}", response));
    }
}
