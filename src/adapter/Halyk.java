package adapter;
import utilities.Money;
import interfaces.Payment;

public class Halyk implements Payment{
    @Override
    public boolean pay(String customerName, Money amount) {
        System.out.println("Halyk:" + customerName + "pays" + amount);
        return true;
    }
}
