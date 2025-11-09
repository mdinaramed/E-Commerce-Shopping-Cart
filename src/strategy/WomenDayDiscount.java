package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class WomenDayDiscount implements DiscountStrategy {
    private boolean isWomenDay(Order order) {
        return order != null && order.date != null && order.date.getMonthValue() == 3 && order.date.getDayOfMonth() == 8;
    }

    @Override
    public Money apply(Money amount, Order order) {
        if (isWomenDay(order))
            return amount.multiply(0.8);
        return amount;
    }
}
