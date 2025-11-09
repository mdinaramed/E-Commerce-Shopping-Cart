package interfaces;
import strategy.Order;
import utilities.Money;

public interface Pricing {
    Money total(PricedItem item, Order order);
}
