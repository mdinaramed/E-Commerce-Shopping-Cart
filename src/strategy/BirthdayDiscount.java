package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;
import java.time.MonthDay;

public class BirthdayDiscount implements DiscountStrategy {
    private boolean isBirthday(Order order) {
        if (order == null || order.date == null || order.birthday == null) {
            return false;
        }
        MonthDay today = MonthDay.from(order.date);
        MonthDay birth = MonthDay.from(order.birthday);
        return today.equals(birth);
    }
    @Override
    public Money apply(Money amount, Order order) {
        if (isBirthday(order))
            return amount.multiply(0.9);
        return amount;
    }
}
