package strategy;
import java.time.LocalDate;

public class Order {
    public final LocalDate date;
    public final LocalDate birthday;
    public final String deliveryType;
    public final int items;
    public String customerId;

    public Order(LocalDate date, LocalDate birthday, String deliveryType, int items, String customerId) {
        this.date = date;
        this.birthday = birthday;
        this.deliveryType = deliveryType;
        this.items = items;
        this.customerId = customerId;
    }
}
