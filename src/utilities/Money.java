package utilities;

public class Money {
    private final double amount;

    public Money(double amount) {
        this.amount = amount;
    }
    public static Money of(double amount) {
        return new Money(amount);
    }
    public double getAmount() {
        return amount;
    }
    public Money add(Money other) {
        return new Money(this.amount + (other == null ? 0.0 : other.amount));
    }
    public Money multiply(double factor) {
        return new Money(this.amount * factor);
    }
    @Override
    public String toString() {
        return String.format("%.0f KZT", amount);

    }
}
