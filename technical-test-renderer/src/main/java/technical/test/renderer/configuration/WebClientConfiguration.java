package technical.test.renderer.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfiguration {

  private final TechnicalApiConfiguration technicalApiConfiguration;

  @Bean
  public WebClient flightApiWebClient() {
    return WebClient.builder()
                    .baseUrl(technicalApiConfiguration.getUrl() + technicalApiConfiguration.getFlightPath())
                    .build();
  }

  @Bean
  public WebClient airportApiWebClient() {
    return WebClient.builder()
                    .baseUrl(technicalApiConfiguration.getUrl() + technicalApiConfiguration.getAirportPath())
                    .build();
  }


}