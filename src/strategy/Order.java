package strategy;
import utilities.Money;
import java.time.LocalDate;

public class Order {
    public final LocalDate date;
    public final LocalDate birthday;
    public final String deliveryType;
    public final int items;

    public Order(LocalDate date, LocalDate birthday, String deliveryType, int items) {
        this.date = date;
        this.birthday = birthday;
        this.deliveryType = deliveryType;
        this.items = items;
    }
}
