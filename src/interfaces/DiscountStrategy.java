package interfaces;
import strategy.Order;
import utilities.Money;

public interface DiscountStrategy {
    Money apply(Money amount, Order order);
}
