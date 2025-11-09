package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class CashbackDiscount implements DiscountStrategy {
    @Override
    public Money apply(Money amount, Order order) {
        if (order == null)
            return amount;
        order.addCashback(amount.multiply(0.95));
        return amount;
    }
}
