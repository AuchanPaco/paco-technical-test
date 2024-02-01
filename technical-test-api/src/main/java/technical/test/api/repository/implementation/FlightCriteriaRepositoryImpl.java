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
        setSubCriterion("origin",origin,query);
        setSubCriterion("destination",destination,query);
        setPagination(page, query);

        return  mongoTemplate.find(query, FlightRecord.class);
    }

    public void setPagination(int page, Query query){
        Pageable pageable = PageRequest.of(page,2, Sort.Direction.ASC,"departure");
        query.with(pageable);
    }

    private void setPriceCriterion(Double price, Query query){
        if (price == null || price <=0) return  ;
        query.addCriteria(Criteria.where("price").is(price));
    }

    private void setSubCriterion(String innerField, String value, Query query){
        if (value == null || value.isBlank()) return  ;
        query.addCriteria(Criteria.where(innerField).is(value));
    }
}
