package technical.test.api.repository.implementation;

import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
            Integer page
    ) {

        Query query = new Query();
        setPriceCriterion(price, query);
        setSubCriterion(ORIGIN_FIELD,origin,query);
        setSubCriterion(DESTINATION_FIELD,destination,query);
        setPagination(page, query);

        return  mongoTemplate.find(query, FlightRecord.class);
    }

    @Override
    public Mono<Page<FlightRecord>> findAllFlightsByPriceAndOriginAndDestinationPageable(
            Double price,
            String origin,
            String destination,
            Pageable page
    ) {


        Pageable finalPage = PageRequest.of(page.getPageNumber(), PAGE_SIZE, page.getSort());

        Query query = getQuery(price, origin, destination, page);

        Flux<FlightRecord> flightRecordFlux = mongoTemplate.find(query, FlightRecord.class);
        Mono<Long> total = mongoTemplate.count( query, FlightRecord.class);



        return flightRecordFlux.collectList()
                .zipWith(total)
                .map(t->new PageImpl<FlightRecord>(t.getT1(), finalPage,t.getT2()))
                .map(pageImpl->pageImpl);

    }



    private Query getQuery(Double price, String origin, String destination, Pageable page) {
        Query query = new Query();
        setPriceCriterion(price, query);
        setSubCriterion(ORIGIN_FIELD, origin,query);
        setSubCriterion(DESTINATION_FIELD, destination,query);
        query.with(page);
        return query;
    }



    public void setPagination(Integer page, Query query){
        if (page==null) return ;
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
