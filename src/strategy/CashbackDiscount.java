package strategy;
import interfaces.DiscountStrategy;
import utilities.Money;

public class CashbackDiscount implements DiscountStrategy {
    @Override
    public Money apply(Money amount, Order order) {
        if (order == null)
            return amount;
        return amount.multiply(0.95);
    }
}
