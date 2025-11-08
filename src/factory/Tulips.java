package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Tulips implements FlowerFactory {
    @Override
    public Flower collectFlower(String color) {
        return new Flower("Tulips", color, Money.of(960));
    }
}
