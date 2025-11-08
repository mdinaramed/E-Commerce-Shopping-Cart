package decorator;
import interfaces.PricedItem;
import utilities.Money;

public class Balloon extends AbstractBouquet{
    public Balloon(PricedItem item) {
        super(item);
    }
    @Override
    public String title(){
        return item.title() +" + Balloon";
    }
    @Override
    public Money price() {
        return item.price().add(Money.of(800));
    }
}
