package adapter;
import interfaces.Delivery;

public class PickUp implements Delivery {
    @Override
    public void deliver(String customerName, String address) {
        System.out.println("SelfPickUp customerName: " + customerName);
    }
}
