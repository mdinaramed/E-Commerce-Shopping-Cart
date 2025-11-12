package bonus;
import interfaces.DiscountStrategy;
import strategy.Order;
import utilities.Money;
import java.util.HashMap;
import java.util.Map;

public class Bonus implements DiscountStrategy {
    private static final Map<String, BonusCard> cards = new HashMap<>();

    @Override
    public Money apply(Money amount, Order order) {
        if (order == null || amount == null) {
            return amount;
        }

        String customerId = String.valueOf(order.customerId);

        BonusCard card = cards.get(customerId);
        if (card == null) {
            card = new BonusCard(customerId);
            cards.put(customerId, card);
        }

        Money earned = amount.multiply(0.05);
        card.add(earned);

        System.out.println("Added bonuses: " + earned);
        return amount;
    }

    public static double getBalance(String customerId) {
        BonusCard card = cards.get(customerId);
        if (card != null)
            return card.getBalance().getAmount();
        else
            return 0.0;
    }
}
