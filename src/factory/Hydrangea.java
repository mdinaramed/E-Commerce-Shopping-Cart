package factory;

import interfaces.FlowerFactory;
import utilities.Money;

public class Hydrangea implements FlowerFactory {
    @Override
    public Flower createFlower(String color) {
        return new Flower("Hydrangea", color, Money.of(1100));
    }
}
