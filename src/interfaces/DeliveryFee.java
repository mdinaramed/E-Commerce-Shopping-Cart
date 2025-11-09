package interfaces;
import strategy.Order;
import utilities.Money;

public interface DeliveryFee {
    Money fee(Money amount, Order order);
}
