package strategy;
import interfaces.Delivery;
import utilities.Money;

public class DeliveryFee implements Delivery {
    @Override
    public Money fee(Money amount, OrderContext orderContext) {
        if (orderContext == null || orderContext.DeliveryType == null)
            return amount;

        switch (orderContext.DeliveryType.toLowerCase()) {
            case "express":
                return amount.add(new Money(2500));
            case "courier":
                return amount.add(new Money(1500));
            default:
                return amount;
        }
    }
}
