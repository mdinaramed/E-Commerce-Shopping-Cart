package factory;
import utilities.Money;

public class Flower {
    private final String name;
    private final String color;
    private final Money price;

    public Flower(String name, String color, Money price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public Money getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " " + color + " " + price + "Tenge";
    }
}
