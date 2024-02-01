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
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        model.addAttribute("flights", this.flightFacade.getFlights());
        return Mono.just("pages/index");
    }

    @GetMapping("/flight")
    public Mono<String> getCreateFlightPage(final Model model ) {
        model.addAttribute("airports", this.flightFacade.getAirports());
        model.addAttribute("flight", new FlightViewModel());
        return Mono.just("pages/flight");
    }

    @PostMapping(value = "/flight")
    public Mono<String> postFlight( Model model, @ModelAttribute(name = "flight")  FlightViewModel flightViewModel){

        return Mono.just("redirect:/");
    }


}
