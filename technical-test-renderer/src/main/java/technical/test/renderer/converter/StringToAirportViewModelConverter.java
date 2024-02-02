package technical.test.renderer.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.AirportService;
import technical.test.renderer.viewmodels.AirportViewModel;

@Component
public class StringToAirportViewModelConverter implements Converter<String, Mono<AirportViewModel>> {

    @Autowired
    private   AirportService airportService;

    public StringToAirportViewModelConverter( ) {
    }

    @Override
    public Mono<AirportViewModel> convert(String iata) {
        return airportService.getAirportByIata(iata);
    }
}

