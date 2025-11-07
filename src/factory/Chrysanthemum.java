package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Chrysanthemum implements FlowerFactory {
    @Override
    public Flower createFlower(String color) {
        return new Flower("Chrysanthemum", color, Money.of(500));
    }
}
