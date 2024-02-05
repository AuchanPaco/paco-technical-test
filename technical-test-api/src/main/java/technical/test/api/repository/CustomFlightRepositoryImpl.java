package technical.test.api.repository;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.request.Pagination;

@AllArgsConstructor
public class CustomFlightRepositoryImpl implements CustomFlightRepository{

  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Flux<FlightRecord> getFlightByCriterias(final List<Criteria> criterias, final Sort sort, final Pagination pagination) {

    Query query = criterias.isEmpty() ?
                  new Query() :
                  new Query(new Criteria().andOperator(criterias));

    query = query.with(sort);

    if(Objects.nonNull(pagination)) {
      query = query.skip(pagination.getSkippedDocuments()).limit(pagination.size());
    }

    return this.reactiveMongoTemplate.find(query, FlightRecord.class);
  }
}
