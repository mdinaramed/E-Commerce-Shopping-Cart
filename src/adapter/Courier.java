package adapter;
import interfaces.Delivery;

public class Courier implements Delivery {
    @Override
    public void deliver(String customerName, String address) {
        System.out.println("Courier delivering " + customerName + " to: " + address);
    }
}
