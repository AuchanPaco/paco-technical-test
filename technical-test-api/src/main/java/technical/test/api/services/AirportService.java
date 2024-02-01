package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.AirportRecord;
import technical.test.api.repository.AirportRepository;

@Service
@RequiredArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public Mono<AirportRecord> findByIataCode(final String iataCode) {
        System.err.println("finding by iata code:"+iataCode);
        return airportRepository.findAirportRecordByIata(iataCode);
//        return airportRepository.findById(iataCode);
    }

    public Mono<AirportRecord> getOrCreate(AirportRecord origin) {
         if(origin.getIata()==null || origin.getIata().isBlank()) return  airportRepository.save(origin);
         return airportRepository.findAirportRecordByIata(origin.getIata()) ;
    }

    public Flux<AirportRecord> getAllAirports() {
        return airportRepository.findAll();
    }
}
