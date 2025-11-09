package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;
import java.time.MonthDay;

public class BirthdayDiscount implements DiscountStrategy {
    private boolean isBirthday(Order order) {
        return order != null && order.date != null && order.birthday != null && MonthDay.from(order.date).equals(order.birthday);
    }

    @Override
    public Money apply(Money amount, Order order) {
        if (isBirthday(order))
            return amount.multiply(0.9);
        return amount;
    }
}
