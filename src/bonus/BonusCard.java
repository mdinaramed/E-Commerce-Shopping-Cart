package bonus;
import utilities.Money;

public class BonusCard {
    public String customerId;
    private Money balance = Money.of(0);

    public BonusCard(String customerId) {

        this.customerId = customerId;
    }
    public void add(Money amount) {
        if (amount != null)
            balance = balance.add(amount);
    }
    public Money getBalance() {
        return balance;
    }
}
