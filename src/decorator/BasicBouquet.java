package decorator;
import interfaces.Bouquet;
import interfaces.PricedItem;
import utilities.Money;

public class BasicBouquet implements PricedItem {
    private final Bouquet bouquet;

    public BasicBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }
    @Override
    public String title() {
        return bouquet.flower() + " " +bouquet.color() + ", wrap: " +bouquet.wrap() + ", card: " +bouquet.card();
    }

    @Override
    public Money price() {
        return bouquet.basePrice();
    }
}
