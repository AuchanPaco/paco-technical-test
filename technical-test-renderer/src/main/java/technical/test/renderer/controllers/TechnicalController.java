package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.AirportFacade;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    private final FlightFacade flightFacade;
    private final AirportFacade airportFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        model.addAttribute("flights", this.flightFacade.getFlights());
        return Mono.just("pages/index");
    }

    @GetMapping("/add-flight")
    public Mono<String> getAddFlightPage(final Model model) {
        model.addAttribute("flight", new FlightViewModel());
        model.addAttribute("airports", this.airportFacade.getAirports());
        return Mono.just("pages/addFlight");
    }

    @PostMapping("/add-flight")
    public Mono<String> createFlight(final Model model, @ModelAttribute("flightViewModel") FlightViewModel flightViewModel) {
        Mono<FlightViewModel> createdFlight = this.flightFacade.createFlight(flightViewModel);
        model.addAttribute("createdFlight", createdFlight);
        return Mono.just("redirect:/");
    }
}
