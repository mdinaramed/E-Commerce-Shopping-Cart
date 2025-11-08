package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Peony implements FlowerFactory {
    @Override
    public Flower collectFlower(String color) {
        return new Flower("Peony", color, Money.of(3000));
    }
}
