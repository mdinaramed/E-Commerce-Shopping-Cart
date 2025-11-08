package factory;
import interfaces.FlowerFactory;
import utilities.Money;

public class Chrysanthemum implements FlowerFactory {
    @Override
    public Flower collectFlower(String color) {
        return new Flower("Chrysanthemum", color, Money.of(700));
    }
}
