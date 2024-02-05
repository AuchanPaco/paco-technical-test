package technical.test.api;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import technical.test.api.constants.RequestParameterConstants;
import technical.test.api.controller.AirportController;
import technical.test.api.controller.FlightController;

@Component
@AllArgsConstructor
public class Router {

  private final FlightController flightController;
  private final AirportController airportController;

  @Bean
  public RouterFunction<ServerResponse> generateRouter() {
    return RouterFunctions.route()
                          .GET("/flight", this.flightController::getFlights)
                          .GET("/flight/{id}", this.flightController::getFlightById)
                          .GET("/airport", this.airportController::getAirports)
                          .POST("/flight", this.flightController::postFlight)
                          .build();
  }

}
