package strategy;
import java.time.LocalDate;

public class OrderContext {
    public final LocalDate date;
    public final LocalDate birthday;
    public final String gender;
    public final String region;
    public final String DeliveryType;
    public final int items;

    public OrderContext(LocalDate date, LocalDate birthday, String gender, String region, String DeliveryType, int items) {
        this.date = date;
        this.birthday = birthday;
        this.gender = gender;
        this.region = region;
        this.DeliveryType = DeliveryType;
        this.items = items;
    }
}
