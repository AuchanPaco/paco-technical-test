package technical.test.renderer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import technical.test.renderer.dto.FlightDto;
import technical.test.renderer.facades.FlightFacade;

@AllArgsConstructor
@Controller
@RequestMapping("/add_flight")
public class AddFlightController {

  private FlightFacade flightFacade;

  @GetMapping
  public Mono<String> getAddFlightPage(final Model model) {
    model.addAttribute("flightDto", new FlightDto());
    return this.flightFacade.getAirports()
                            .collectList()
                            .doOnNext(airports -> model.addAttribute("airports", airports))
                            .thenReturn("pages/add_flight");
  }

  @PostMapping
  public Mono<String> submitFlight(@ModelAttribute FlightDto flightDto) {
    return this.flightFacade.postFlight(flightDto)
                            .map(response -> "redirect:/");
  }

}
