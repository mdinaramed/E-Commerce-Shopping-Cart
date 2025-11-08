package interfaces;
import utilities.Money;

public interface Bouquet {
    String flower();
    String color();
    String wrap();
    String card();
    Money basePrice();
}
