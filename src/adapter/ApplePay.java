package adapter;
import utilities.Money;
import interfaces.Payment;

public class ApplePay implements Payment {
    @Override
    public boolean pay(String customerName, Money amount) {
        System.out.println("ApplePay:" + customerName + "pays" + amount);
        return true;
    }
}
