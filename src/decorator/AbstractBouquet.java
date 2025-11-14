package decorator;
import interfaces.Bouquet;
import interfaces.PricedItem;
import utilities.Money;

public abstract class AbstractBouquet implements PricedItem {
    protected PricedItem item;

    public AbstractBouquet(PricedItem item) {
        this.item = item;
    }
    @Override
    public String title() {
        return item.title();
    }
    @Override
    public Money price() {
        return item.price();
    }
}
