package technical.test.api.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import technical.test.api.facade.FlightFacade;

@Controller
@AllArgsConstructor
@Slf4j
public class AirportController {

  private final FlightFacade flightFacade;

  public Mono<ServerResponse> getAirports(ServerRequest serverRequest) {
    log.info("Retrieve airports");
    return this.flightFacade.getAirports()
                            .flatMap(airports -> ServerResponse.ok().bodyValue(airports));
  }

}
