package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Orchid implements FlowerFactory {
    @Override
    public Flower createFlower(String color) {
        return new Flower("Orchid", color, Money.of(1090));
    }
}
