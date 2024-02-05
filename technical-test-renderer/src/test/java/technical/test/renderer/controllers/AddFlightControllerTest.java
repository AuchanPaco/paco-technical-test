package technical.test.renderer.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.AirportViewModel;

class AddFlightControllerTest {

  @Test
  void shouldReturnGetAddFlightPage() {
    FlightFacade flightFacade = mock(FlightFacade.class);
    Model        model        = new ConcurrentModel();
    when(flightFacade.getAirports()).thenReturn(Flux.just(new AirportViewModel()));

    AddFlightController addFlightController = new AddFlightController(flightFacade);
    StepVerifier.create(addFlightController.getAddFlightPage(model))
                .expectNextMatches(viewName -> Objects.equals("pages/add_flight", viewName) &&
                                               model.containsAttribute("flightDto") &&
                                               model.containsAttribute("airports"))
                .expectComplete()
                .verify();
  }

  @Test
  void shouldRedirectToHomeController() {
    FlightFacade flightFacade = mock(FlightFacade.class);
    when(flightFacade.postFlight(any())).thenReturn(Mono.just(ResponseEntity.ofNullable(null)));

    AddFlightController addFlightController = new AddFlightController(flightFacade);
    StepVerifier.create(addFlightController.submitFlight(null))
                .expectNextMatches(viewName -> Objects.equals("redirect:/", viewName))
                .expectComplete()
                .verify();
  }

}
