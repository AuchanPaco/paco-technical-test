package technical.test.renderer.viewmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class FiltersViewModel {
    private Optional<Double> price;
    private Optional<String> origin;
    private Optional<String> destination;
    private Optional<String> priceSort;
    private Optional<String> originSort;
    private Optional<String> destinationSort;
}
