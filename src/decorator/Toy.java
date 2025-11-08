package decorator;
import interfaces.PricedItem;
import utilities.Money;

public class Toy extends AbstractBouquet{
    public Toy(PricedItem item) {
        super(item);
    }
    @Override
    public String title() {
        return item.title() + " + Toy";
    }
    @Override
    public Money price() {
        return item.price().add(Money.of(6000));
    }
}
