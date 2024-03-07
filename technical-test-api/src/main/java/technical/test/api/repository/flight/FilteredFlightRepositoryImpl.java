package technical.test.api.repository.flight;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FiltersRepresentation;

@AllArgsConstructor
public class FilteredFlightRepositoryImpl implements FilteredFlightRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<FlightRecord> findAllByFilters(FiltersRepresentation filtersRepresentation) {
        Query query = new Query();

        filtersRepresentation.price()
                .ifPresent(price ->  query.addCriteria(Criteria.where("price").is(price)));

        filtersRepresentation.origin()
                .ifPresent(origin -> query.addCriteria(Criteria.where("origin").is(origin)));

        filtersRepresentation.destination()
                .ifPresent(destination -> query.addCriteria(Criteria.where("destination").is(destination)));

        filtersRepresentation.priceSort()
                .ifPresent(priceSort -> query.with(Sort.by(Sort.Direction.fromString(priceSort), "price")));

        filtersRepresentation.originSort()
                .ifPresent(originSort -> query.with(Sort.by(Sort.Direction.fromString(originSort), "origin")));

        filtersRepresentation.destinationSort()
                .ifPresent(destinationSort -> query.with(Sort.by(Sort.Direction.fromString(destinationSort), "destination")));

        return this.mongoTemplate.find(query, FlightRecord.class);
    }
}
