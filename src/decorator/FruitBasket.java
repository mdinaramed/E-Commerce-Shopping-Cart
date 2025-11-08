package decorator;
import interfaces.PricedItem;
import utilities.Money;

public class FruitBasket extends AbstractBouquet{
    public FruitBasket(PricedItem item) {
        super(item);
    }
    @Override
    public String title(){
        return item.title() + " + Fruit Basket";
    }
    @Override
    public Money price() {
        return item.price().add(Money.of(13000));
    }
}
