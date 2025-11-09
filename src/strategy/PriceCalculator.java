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
        Money sum=item.price();
        for (DiscountStrategy discount : discounts) {
            sum=discount.apply(sum, order);
        }
        sum=sum.add(deliveryFee.fee(sum, order));
        return sum;
    }
}
