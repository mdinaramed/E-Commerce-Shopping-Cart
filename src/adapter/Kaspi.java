package adapter;
import utilities.Money;
import interfaces.Payment;

public class Kaspi implements Payment{
    @Override
    public boolean pay(String customerName, Money amount) {
        System.out.println("Kaspi: " + customerName + " pays " + amount);
        return true;
    }
}
