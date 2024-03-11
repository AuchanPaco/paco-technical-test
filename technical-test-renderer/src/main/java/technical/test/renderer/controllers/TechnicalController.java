package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.AirportFacade;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FiltersViewModel;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    private final FlightFacade flightFacade;
    private final AirportFacade airportFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        return getMarketPlaceWithFlights(model, this.flightFacade.getFlights());
    }

    @GetMapping("/add-flight")
    public Mono<String> getAddFlightPage(final Model model) {
        model.addAttribute("flight", new FlightViewModel());
        model.addAttribute("airports", this.airportFacade.getAirports());
        return Mono.just("pages/addFlight");
    }

    @PostMapping
    public Mono<String> createFlight(final Model model, @ModelAttribute("flightViewModel") FlightViewModel flightViewModel) {
        Mono<FlightViewModel> createdFlight = this.flightFacade.createFlight(flightViewModel);
        model.addAttribute("createdFlight", createdFlight);
        return Mono.just("redirect:/");
    }

    @GetMapping("/filters")
    public Mono<String> getAllFilteredFlights(final Model model, @ModelAttribute("filtersViewModel") FiltersViewModel filtersViewModel) {
        return getMarketPlaceWithFlights(model, this.flightFacade.getAllFilteredFlights(filtersViewModel), filtersViewModel);
    }

    @GetMapping("/{id}")
    public Mono<String> getFlightById(final Model model, @PathVariable("id") UUID id) {
        model.addAttribute("flight", this.flightFacade.getFlightById(id));
        return Mono.just("pages/flight");
    }

    private Mono<String> getMarketPlaceWithFlights(final Model model, final Flux<FlightViewModel> flights, final FiltersViewModel filtersViewModel) {
        model.addAttribute("flights", flights);
        model.addAttribute("airports", this.airportFacade.getAirports());
        model.addAttribute("filters", filtersViewModel);
        return Mono.just("pages/index");
    }

    private Mono<String> getMarketPlaceWithFlights(final Model model, final Flux<FlightViewModel> flights) {
        return this.getMarketPlaceWithFlights(model, flights, new FiltersViewModel());
    }
}
