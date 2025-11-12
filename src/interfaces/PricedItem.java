package interfaces;
import utilities.Money;

public interface PricedItem {
    String title();
    Money price();

    default String breakdown() {
        return title() + ": " + price();
    }
}
