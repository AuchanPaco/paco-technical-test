package technical.test.api.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import technical.test.api.record.FlightRecord;
import technical.test.api.request.Pagination;

public interface CustomFlightRepository {

  Flux<FlightRecord> getFlightByCriterias(final List<Criteria> criterias, final Sort sort, final Pagination pagination);

}
