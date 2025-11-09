package strategy;
import utilities.Money;
import java.time.LocalDate;

public class Order {
    public final LocalDate date;
    public final LocalDate birthday;
    public final String DeliveryType;
    public final int items;
    private int quantity;
    private Money cashback = Money.zero();

    public Order(LocalDate date, LocalDate birthday, String DeliveryType, int items) {
        this.date = date;
        this.birthday = birthday;
        this.DeliveryType = DeliveryType;
        this.items = items;
        this.quantity = items;
    }

    public int quantity() {
        return quantity;
    }

    public void addCashback(Money amount) {
        this.cashback = this.cashback.add(amount);
    }

    public Money cashback() {
        return cashback;
    }
}
