package strategy;
import interfaces.*;
import utilities.Money;
import java.util.List;

public class PriceCalculator implements Pricing {
    private final List<DiscountStrategy> discounts;
    private final Delivery delivery;

    public PriceCalculator(List<DiscountStrategy> discounts, Delivery delivery) {
        this.discounts = discounts;
        this.delivery = delivery;
    }

    @Override
    public Money total(PricedItem item, OrderContext orderContext) {
        Money sum=item.price();
        for (DiscountStrategy discount : discounts) {
            sum=discount.apply(sum, orderContext);
        }
        sum=sum.add(delivery.fee(sum, orderContext));
        return sum;
    }
}
