package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.AirportFacade;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.FlightResponse;
import technical.test.renderer.viewmodels.FlightViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {

    private final FlightFacade flightFacade;

    private final AirportFacade airportFacade;

    @GetMapping
    public Mono<String> getMarketPlaceReturnCouponPage(final Model model,
                                                       @RequestParam("page") Optional<Integer> page,
                                                       @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        var flightResponse = this.flightFacade.getFlights(currentPage - 1, pageSize);
        model.addAttribute("flights", flightResponse.map(FlightResponse::getContent));
        model.addAttribute("totalPages", flightResponse.map(FlightResponse::getTotalPages));
        model.addAttribute("actualPage", flightResponse.map(FlightResponse::getNumber));
        var pageNumbers = flightResponse.filter(response -> response.getTotalPages() > 0)
                .map(response -> IntStream.rangeClosed(1, response.getTotalPages())
                        .boxed()
                        .collect(Collectors.toList()));

        model.addAttribute("pageNumbers", pageNumbers);
        return Mono.just("pages/index");
    }

    @GetMapping("/createFlight")
    public Mono<String> getCreateFlightPage(Model model) {
        model.addAttribute("newFlight", new FlightViewModel());
        model.addAttribute("airports", this.airportFacade.getAirports());
        return Mono.just("pages/createFlightsForm");
    }

    @PostMapping("/createFlight")
    public Mono<String> createFlight(@ModelAttribute FlightViewModel newFlight) {
        this.flightFacade.createFlights(newFlight);
        return Mono.just("redirect:/");
    }
}
