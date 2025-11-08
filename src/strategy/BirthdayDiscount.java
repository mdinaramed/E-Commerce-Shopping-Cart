package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;
import java.time.MonthDay;

public class BirthdayDiscount implements DiscountStrategy {
    private boolean isBirthday(OrderContext orderContext) {
        return orderContext != null && orderContext.date != null && orderContext.birthday != null
                && MonthDay.from(orderContext.date).equals(orderContext.birthday);
    }

    @Override
    public Money apply(Money amount, OrderContext orderContext) {
        if (isBirthday(orderContext))
            return amount.multiply(0.9);
        return amount;
    }
}
