package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Rose implements FlowerFactory {
    @Override
    public Flower collectFlower(String color) {
        return new Flower("Rose", color, Money.of(800));
    }
}
