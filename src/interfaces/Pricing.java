package interfaces;
import strategy.OrderContext;
import utilities.Money;

public interface Pricing {
    Money total(PricedItem item, OrderContext orderContext);
}
