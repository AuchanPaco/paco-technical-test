package technical.test.renderer.controllers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import technical.test.renderer.dto.SelectedFlightFiltersDto;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

class DetailFlightControllerTest {

  @Test
  void shouldReturnGetFlightDetailView() {
    FlightFacade             flightFacade             = mock(FlightFacade.class);
    Model                    model                    = new ConcurrentModel();
    FlightViewModel          flightViewModel          = new FlightViewModel();
    SelectedFlightFiltersDto selectedFlightFiltersDto = new SelectedFlightFiltersDto();

    when(flightFacade.getFlightById(any())).thenReturn(Mono.just(flightViewModel));

    DetailFlightController detailFlightController = new DetailFlightController(flightFacade);
    StepVerifier.create(detailFlightController.getFlightDetail(model, selectedFlightFiltersDto))
                .expectNextMatches(viewName -> Objects.equals("pages/detailed_flight", viewName) &&
                                               model.containsAttribute("flightFilters") &&
                                               Objects.equals(selectedFlightFiltersDto, model.getAttribute("flightFilters")) &&
                                               model.containsAttribute("flight") &&
                                               Objects.equals(flightViewModel, model.getAttribute("flight")))
                .expectComplete()
                .verify();
  }

  @Test
  void shouldReturnSameForGetAndPost() {
    FlightFacade             flightFacade             = mock(FlightFacade.class);
    Model                    model                    = new ConcurrentModel();
    FlightViewModel          flightViewModel          = new FlightViewModel();
    SelectedFlightFiltersDto selectedFlightFiltersDto = new SelectedFlightFiltersDto();

    when(flightFacade.getFlightById(any())).thenReturn(Mono.just(flightViewModel));

    DetailFlightController detailFlightController = new DetailFlightController(flightFacade);

    assertSame(detailFlightController.getFlightDetail(model, selectedFlightFiltersDto).block(),
               detailFlightController.postFlightDetail(model, selectedFlightFiltersDto).block());
  }

}
