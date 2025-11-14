package strategy;
import utilities.Money;
import java.time.MonthDay;

public class Delivery implements interfaces.DeliveryFee {
    private boolean isBirthday(Order order) {
        return order != null && order.date != null && order.birthday != null && MonthDay.from(order.date).equals(order.birthday);
    }

    @Override
    public Money fee(Money amount, Order order) {
        if (order == null || order.deliveryType == null) {
            return Money.of(0);
        }

        if (isBirthday(order)) {
            return Money.of(0);
        }

        double fee = switch (order.deliveryType.toLowerCase()) {
            case "express" -> 2500;
            case "courier" -> 1500;
            default -> 0;
        };
        return Money.of(fee);
    }
}
