package strategy;
import utilities.Money;

public class DeliveryFee implements interfaces.DeliveryFee {
    @Override
    public Money fee(Money amount, Order order) {
        if (order == null || order.DeliveryType == null)
            return amount;

        switch (order.DeliveryType.toLowerCase()) {
            case "express":
                return amount.add(new Money(2500));
            case "courier":
                return amount.add(new Money(1500));
            default:
                return amount;
        }
    }
}
