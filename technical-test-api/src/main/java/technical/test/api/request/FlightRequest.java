package technical.test.api.request;

import io.vavr.control.Try;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import technical.test.api.constants.RequestParameterConstants;
import technical.test.api.exception.ClientException;
import technical.test.api.exception.clientexception.InvalidDateParamException;
import technical.test.api.exception.clientexception.InvalidNumberParamException;
import technical.test.api.exception.clientexception.InvalidParamException;

public class FlightRequest {

  private FlightRequest(){}

  public static List<Criteria> extractGetFlightCriterias(final Map<String, String> requestParams) throws ClientException {

    final String minPriceStr = requestParams.get(RequestParameterConstants.MINIMAL_PRICE);
    final String maxPriceStr = requestParams.get(RequestParameterConstants.MAXIMAL_PRICE);
    final String origin      = requestParams.get(RequestParameterConstants.ORIGIN);
    final String destination = requestParams.get(RequestParameterConstants.DESTINATION);
    final String minDateStr = requestParams.get(RequestParameterConstants.MINIMAL_DATE);
    final String maxDateStr = requestParams.get(RequestParameterConstants.MAXIMAL_DATE);

    Double minPrice = null;
    Double maxPrice = null;

    LocalDateTime minDate = null;
    LocalDateTime maxDate = null;

    List<Criteria> criterias = new ArrayList<>();

    if (StringUtils.isNotBlank(minPriceStr)) {
      if (!NumberUtils.isCreatable(minPriceStr)) {
        throw new InvalidNumberParamException(RequestParameterConstants.MINIMAL_PRICE);
      }
      minPrice = Double.parseDouble(minPriceStr);
      criterias.add(Criteria.where("price").gte(minPrice));
    }
    if (StringUtils.isNotBlank(maxPriceStr)) {
      if (!NumberUtils.isCreatable(maxPriceStr)) {
        throw new InvalidNumberParamException(RequestParameterConstants.MAXIMAL_PRICE);
      }
      maxPrice = Double.parseDouble(maxPriceStr);
      criterias.add(Criteria.where("price").lte(maxPrice));
    }
    if (StringUtils.isNotBlank(origin)) {
      criterias.add(Criteria.where("origin").is(origin));
    }
    if (StringUtils.isNotBlank(destination)) {
      criterias.add(Criteria.where("destination").is(destination));
    }
    if(StringUtils.isNotBlank(minDateStr)) {
      try{
        minDate = LocalDateTime.parse(minDateStr);
      } catch(DateTimeParseException e) {
        throw new InvalidDateParamException(RequestParameterConstants.MINIMAL_DATE);
      }
      criterias.add(Criteria.where("departure").gte(LocalDateTime.parse(minDateStr)));
    }
    if(StringUtils.isNotBlank(maxDateStr)) {
      try{
        maxDate = LocalDateTime.parse(maxDateStr);
      } catch(DateTimeParseException e) {
        throw new InvalidDateParamException(RequestParameterConstants.MAXIMAL_DATE);
      }
      criterias.add(Criteria.where("departure").lte(LocalDateTime.parse(maxDateStr)));
    }

    if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice) && minPrice > maxPrice) {
      throw new InvalidParamException(List.of(RequestParameterConstants.MINIMAL_PRICE,
                                              RequestParameterConstants.MAXIMAL_PRICE),
                                      "Minimal price should be lower than maximal price");
    }

    if(Objects.nonNull(minDate) && Objects.nonNull(maxDate) && minDate.isAfter(maxDate)) {
      throw new InvalidParamException(List.of(RequestParameterConstants.MINIMAL_DATE,
                                              RequestParameterConstants.MAXIMAL_DATE),
                                      "Minimal date should be before than maximal date");
    }

    return criterias;
  }

  public static Pagination extractPagination(final Map<String, String> requestParams) {
    final String pageStr = requestParams.get(RequestParameterConstants.PAGE);
    final String sizeStr = requestParams.get(RequestParameterConstants.SIZE);

    int page = 1;
    int size = 0;

    if(StringUtils.isNotBlank(pageStr)) {
      if(!NumberUtils.isDigits(pageStr)) {
        throw new InvalidNumberParamException(RequestParameterConstants.PAGE);
      }
      page = Integer.parseInt(pageStr);
    }

    if(StringUtils.isNotBlank(sizeStr)) {
      if(!NumberUtils.isDigits(sizeStr)) {
        throw new InvalidNumberParamException(RequestParameterConstants.SIZE);
      }
      size = Integer.parseInt(sizeStr);
    }

    if(page != 1 ||  size != 0) {
      return new Pagination(page, size);
    }
    return null;
  }

}
