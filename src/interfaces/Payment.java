package interfaces;
import utilities.Money;

public interface Payment {
    boolean pay(String customerName, Money amount);
}
