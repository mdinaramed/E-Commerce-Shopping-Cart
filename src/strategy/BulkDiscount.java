package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class BulkDiscount implements DiscountStrategy {
    @Override
    public Money apply(Money amount, OrderContext orderContext) {
        if (orderContext == null)
            return amount;
        if (orderContext.items >= 50)
            return amount.multiply(0.8);
        if (orderContext.items >= 25)
            return amount.multiply(0.9);
        return amount;
    }
}
