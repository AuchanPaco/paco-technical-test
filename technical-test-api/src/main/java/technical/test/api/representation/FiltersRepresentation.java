package technical.test.api.representation;

import java.util.Optional;

public record FiltersRepresentation(
        Optional<Double> price,
        Optional<String> origin,
        Optional<String> destination,
        Optional<String> priceSort,
        Optional<String> originSort,
        Optional<String> destinationSort
) {}
