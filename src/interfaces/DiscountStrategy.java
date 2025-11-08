package interfaces;

import strategy.OrderContext;
import utilities.Money;

public interface DiscountStrategy {
    Money apply(Money amount, OrderContext orderContext);
}
