package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class BulkDiscount implements DiscountStrategy {
    @Override
    public Money apply(Money amount, Order order) {
        if (order == null)
            return amount;
        if (order.items >= 50)
            return amount.multiply(0.8);
        if (order.items >= 25)
            return amount.multiply(0.9);
        return amount;
    }
}
