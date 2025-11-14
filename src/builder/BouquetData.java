package builder;
import interfaces.Bouquet;
import utilities.Money;

public class BouquetData implements Bouquet {
    private final String flower;
    private final String color;
    private final String wrap;
    private final String card;
    private final Money basePrice;

    public BouquetData(String flower, String color, String wrap, String card, Money basePrice) {
        this.flower = flower;
        this.color = color;
        this.wrap = wrap;
        this.card = card;
        this.basePrice = basePrice;
    }
    @Override
    public String flower() {
        return flower;
    }
    @Override
    public String color() {
        return color;
    }
    @Override
    public String wrap() {
        return wrap;
    }
    @Override
    public String card() {
        return card;
    }
    @Override
    public Money basePrice() {
        return basePrice;
    }
    @Override
    public String toString() {
        return flower + " " + color + ", wrap: " + wrap + ", card: " + card + ", price: " + basePrice;
    }
}
