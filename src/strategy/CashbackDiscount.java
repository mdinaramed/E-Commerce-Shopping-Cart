package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class CashbackDiscount implements DiscountStrategy {
    @Override
    public Money apply(Money amount, OrderContext orderContext) {
        if (orderContext == null)
            return amount;
        return amount.multiply(0.95);
    }
}
