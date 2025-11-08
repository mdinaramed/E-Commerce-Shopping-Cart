package decorator;
import interfaces.PricedItem;
import utilities.Money;

public class Cake extends AbstractBouquet{
    public Cake(PricedItem item) {
        super(item);
    }
    @Override
    public String title(){
        return item.title() +" + Cake";
    }
    @Override
    public Money price() {
        return item.price().add(Money.of(4000));
    }
}
