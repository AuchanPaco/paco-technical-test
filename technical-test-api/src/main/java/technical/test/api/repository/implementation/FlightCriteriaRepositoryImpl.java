package technical.test.api.repository.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightCriteriaRepository;

public class FlightCriteriaRepositoryImpl implements FlightCriteriaRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    private static final String ORIGIN_FIELD = "origin";
    private static final String DESTINATION_FIELD = "destination";
    private static final String DEPARTURE_FIELD = "departure";
    private static final String PRICE_FIELD = "price";
    private static final int PAGE_SIZE = 6;

    public FlightCriteriaRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<FlightRecord> findAllFlightsByPriceAndOriginAndDestination(
            Double price,
            String origin,
            String destination,
            int page
    ) {

        Query query = new Query();
        setPriceCriterion(price, query);
        setSubCriterion(ORIGIN_FIELD,origin,query);
        setSubCriterion(DESTINATION_FIELD,destination,query);
        setPagination(page, query);

        return  mongoTemplate.find(query, FlightRecord.class);
    }

    public void setPagination(int page, Query query){
        Pageable pageable = PageRequest.of(page,PAGE_SIZE, Sort.Direction.ASC,DEPARTURE_FIELD);
        query.with(pageable);
    }

    private void setPriceCriterion(Double price, Query query){
        if (price == null || price <=0) return  ;
        query.addCriteria(Criteria.where(PRICE_FIELD).is(price));
    }

    private void setSubCriterion(String innerField, String value, Query query){
        if (value == null || value.isBlank()) return  ;
        query.addCriteria(Criteria.where(innerField).is(value));
    }
}
