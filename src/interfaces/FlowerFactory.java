package interfaces;
import factory.Flower;

public interface FlowerFactory {
    Flower collectFlower(String color);
}
