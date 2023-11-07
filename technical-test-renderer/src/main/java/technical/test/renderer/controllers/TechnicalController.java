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
import technical.test.renderer.request.FlightRequest;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    @Autowired
    private FlightFacade flightFacade;
    private FlightRequest flightRequest;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model) {
        model.addAttribute("flights", this.flightFacade.getFlights());
        return Mono.just("pages/index");
    }

    @PostMapping("/savedFlight")
    public Mono<String> createFlight(final Model model, @ModelAttribute FlightRequest flightRequest) {
        model.addAttribute("flightRequest", flightRequest);
        return Mono.just("pages/savedFlight");
    }

    @GetMapping("/savedFlight")
    public Mono<String> createFlight(final Model model) {
        model.addAttribute("flightRequest", flightRequest);
        return Mono.just("pages/savedFlight");
    }


}
