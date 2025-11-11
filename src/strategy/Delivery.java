package strategy;
import utilities.Money;

public class Delivery implements interfaces.DeliveryFee {
    @Override
    public Money fee(Money amount, Order order) {
        if (order == null || order.deliveryType == null)
            return Money.of(0);;

        switch (order.deliveryType.toLowerCase()) {
            case "express":
                return  Money.of(2500);
            case "courier":
                return Money.of(1500);
            default:
                return Money.of(0);
        }
    }
}