package decorator;
import interfaces.PricedItem;
import utilities.Money;

public class ChocoStrawberry extends AbstractBouquet{
    public ChocoStrawberry(PricedItem item) {
        super(item);
    }
    @Override
    public String title() {
        return item.title() + " + Choco Strawberry";
    }
    @Override
    public Money price() {
        return item.price().add(Money.of(9000));
    }
}
