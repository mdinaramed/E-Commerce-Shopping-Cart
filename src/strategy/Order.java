package strategy;
import java.time.LocalDate;

public class Order {
    public final LocalDate date;
    public final LocalDate birthday;
    public final String region;
    public final String DeliveryType;
    public final int items;

    public Order(LocalDate date, LocalDate birthday, String region, String DeliveryType, int items) {
        this.date = date;
        this.birthday = birthday;
        this.region = region;
        this.DeliveryType = DeliveryType;
        this.items = items;
    }
}
