package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Lily implements FlowerFactory {
    @Override
    public Flower createFlower(String color) {
        return new Flower("Lily", color, Money.of(4200));
    }
}
