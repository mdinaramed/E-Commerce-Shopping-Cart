package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class WomenDayDiscount implements DiscountStrategy {
    private boolean isWomenDay(OrderContext orderContext) {
        return orderContext != null && orderContext.date != null &&
                orderContext.date.getMonthValue() == 3 && orderContext.date.getDayOfMonth() == 8;
    }

    @Override
    public Money apply(Money amount, OrderContext orderContext) {
        if (isWomenDay(orderContext))
            return amount.multiply(0.8);
        return amount;
    }
}
