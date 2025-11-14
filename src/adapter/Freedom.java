package adapter;
import utilities.Money;
import interfaces.Payment;

public class Freedom implements Payment{
    @Override
    public boolean pay(String customerName, Money amount) {
        System.out.println("Freedom: " + customerName + " pays " + amount);
        return true;
    }
}
