package strategy;
import utilities.Money;

public class DeliveryFee implements interfaces.DeliveryFee {
    @Override
    public Money fee(Money amount, Order order) {
        if (order == null || order.DeliveryType == null)
            return Money.zero();

        switch (order.DeliveryType.toLowerCase()) {
            case "express":
                return new Money(2500);
            case "courier":
                return new Money(1500);
            default:
                return Money.zero();
        }
    }
}
