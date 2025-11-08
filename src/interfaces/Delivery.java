package interfaces;
import strategy.OrderContext;
import utilities.Money;

public interface Delivery {
    Money fee(Money amount, OrderContext orderContext);
}
