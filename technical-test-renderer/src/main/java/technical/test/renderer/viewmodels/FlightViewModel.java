package technical.test.renderer.viewmodels;

import lombok.Data;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class FlightViewModel {
    private UUID id;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private double price;
    private String image;
    private AirportViewModel origin;
    private AirportViewModel destination;

    public String getFormattedPrice() {
        return this.getFormattedPrice("€");
    }

    public String getFormattedPrice(String suffix) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        return decimalFormat.format(this.price) + suffix;
    }

    public String getFormattedDeparture() {
        return this.formatLocalDateTime(this.departure);
    }

    public String getFormattedArrival() {
        return this.formatLocalDateTime(this.arrival);
    }

    public String formatLocalDateTime(LocalDateTime localDateTime) {
        return this.formatLocalDateTime(localDateTime, "dd/MM/yyyy HH:mm");
    }

    public String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }
}
