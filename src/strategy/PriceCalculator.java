package strategy;
import interfaces.*;
import interfaces.DeliveryFee;
import utilities.Money;
import java.util.List;

public class PriceCalculator implements Pricing {
    private final List<DiscountStrategy> discounts;
    private final DeliveryFee deliveryFee;

    public PriceCalculator(List<DiscountStrategy> discounts, DeliveryFee deliveryFee) {
        this.discounts = discounts;
        this.deliveryFee = deliveryFee;
    }

    @Override
    public Money total(PricedItem item, Order order) {
        Money perItem = item.price();

        for (DiscountStrategy discount : discounts) {
            perItem = discount.apply(perItem, order);
        }
        Money goodsTotal = perItem.multiply(order == null ? 1 : order.items);
        Money shipping = deliveryFee.fee(goodsTotal, order);
        return goodsTotal.add(shipping);
    }
}
