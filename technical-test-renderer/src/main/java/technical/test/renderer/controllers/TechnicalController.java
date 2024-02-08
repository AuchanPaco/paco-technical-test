package technical.test.renderer.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.facades.FlightFacade;
import technical.test.renderer.viewmodels.AirportViewModel;
import technical.test.renderer.viewmodels.FlightInput;
import technical.test.renderer.viewmodels.FlightViewModel;
import technical.test.renderer.viewmodels.SearchViewModel;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class TechnicalController {



    @Autowired
    private FlightFacade flightFacade;

    @ModelAttribute("airports")
    public Flux<AirportViewModel> populateAirports(){
        return flightFacade.getAirports();
    }


    @GetMapping
    public Mono<String> getFlights(
            final Model model,
            @ModelAttribute(name = "search")  SearchViewModel searchViewModel
    ) {
        model.addAttribute("flights", this.flightFacade.getFlights(searchViewModel));
        model.addAttribute("search", new SearchViewModel());
        return Mono.just("pages/index");
    }

    @GetMapping("filter")
    public Mono<String> filter(
            final Model model,
           @ModelAttribute(name = "search")  SearchViewModel searchViewModel
    ) {
        model.addAttribute("flights", this.flightFacade.getFlights(searchViewModel));
        model.addAttribute("search", searchViewModel);
        return Mono.just("pages/search");
    }



    @ExceptionHandler(Exception.class)
    public Mono<String> handleException(Exception e) {
        log.error("An error occurred", e);
        return Mono.just("error");
    }


    @GetMapping("/flight")
    public Mono<String> getCreateFlightForm(final Model model ) {
        model.addAttribute("flightNew", new FlightInput());
        return Mono.just("pages/flight");
    }

    @PostMapping(value = "/flight-new")
    public Mono<String> postFlight( final Model model,  @ModelAttribute(name = "flightNew")  FlightInput flightNew){
        Mono<FlightViewModel> f = this.flightFacade.createFlight(flightNew);
        model.addAttribute("new", f);
        return Mono.just("redirect:/");
    }


}
