package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Peony implements FlowerFactory {
    @Override
    public Flower createFlower(String color) {
        return new Flower("Peony", color, Money.of(5000));
    }
}
