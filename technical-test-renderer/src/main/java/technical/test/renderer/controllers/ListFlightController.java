package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.dto.FlightDto;
import technical.test.renderer.dto.FlightFiltersDto;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class ListFlightController {

  private final FlightFacade flightFacade;

  @GetMapping
  public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {

    final boolean isFlightFilters = model.containsAttribute("flightFilters");

    final FlightFiltersDto flightFilters = isFlightFilters ?
                                           (FlightFiltersDto) model.getAttribute("flightFilters") :
                                           new FlightFiltersDto();

    final Flux<FlightViewModel> flights = isFlightFilters ?
                                          this.flightFacade.getFlightsWithFilters(flightFilters) :
                                          this.flightFacade.getFlights();

    return Mono.zip(flights.collectList(),
                    this.flightFacade.getAirports().collectList())
               .doOnNext(tuple -> {
                 model.addAttribute("flights", tuple.getT1());
                 model.addAttribute("airports", tuple.getT2());
                 model.addAttribute("flightFilters", isFlightFilters ?
                                                     model.getAttribute("flightFilters") :
                                                     new FlightFiltersDto());
               })
               .thenReturn("pages/index");
  }

  @PostMapping
  public Mono<String> postListFlight(final Model model, @ModelAttribute FlightFiltersDto flightFilters) {
    model.addAttribute("flightFilters", flightFilters);
    return this.getMarketPlaceReturnCouponPage(model);
  }

}
