package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.dto.FlightFiltersDto;
import technical.test.renderer.dto.SelectedFlightFiltersDto;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping("/detail_flight")
@RequiredArgsConstructor
@Slf4j
public class DetailFlightController {

  private final FlightFacade flightFacade;

  @PostMapping
  public Mono<String> postFlightDetail(final Model model, @ModelAttribute SelectedFlightFiltersDto selectedFlightFiltersDto) {
    return this.getFlightDetail(model, selectedFlightFiltersDto);
  }

  @GetMapping
  public Mono<String> getFlightDetail(final Model model, final SelectedFlightFiltersDto selectedFlightFilters) {
    model.addAttribute("flightFilters", selectedFlightFilters);
    return this.flightFacade.getFlightById(selectedFlightFilters.getSelectedId())
                            .doOnNext(flight -> model.addAttribute("flight", flight))
                            .thenReturn("pages/detailed_flight");
  }

}
