package technical.test.api.controller;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.exception.ClientException;
import technical.test.api.facade.FlightFacade;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;

@Controller
@AllArgsConstructor
@Slf4j
public class FlightController {

  private final FlightFacade flightFacade;

  public Mono<ServerResponse> getFlights(ServerRequest request) {
    log.info("Retrieve flights");

    return this.flightFacade.getFlights(request.queryParams().toSingleValueMap())
                            .flatMap(flights -> ServerResponse.ok().bodyValue(flights))
                            .switchIfEmpty(ServerResponse.noContent().build())
                            .onErrorResume(ClientException.class, ClientException::getAsResponse);
  }

  public Mono<ServerResponse> postFlight(ServerRequest request) {
    log.info("Post flight");

    return request.bodyToMono(FlightRecord.class)
                  .flatMap(this.flightFacade::postFlight)
                  .flatMap(record -> ServerResponse.created(URI.create(""))
                                                   .build());
  }

  public Mono<ServerResponse> getFlightById(ServerRequest serverRequest) {
    log.info("Retrieve flight by id : {}", serverRequest.pathVariable("id"));

    return this.flightFacade.getFlightById(serverRequest.pathVariable("id"))
                            .flatMap(flight -> ServerResponse.ok().bodyValue(flight))
                            .switchIfEmpty(ServerResponse.noContent().build())
                            .onErrorResume(ClientException.class, ClientException::getAsResponse);
  }
}
