package builder;
import factory.Flower;
import interfaces.Bouquet;
import interfaces.FlowerFactory;
import utilities.Money;

public class BouquetBuilder{
    private String flower;
    private String color;
    private String wrap;
    private String card;
    private Money basePrice;

    public BouquetBuilder fromFactory(FlowerFactory factory, String color){
        Flower f = factory.collectFlower(color);
        this.flower = f.getName();
        this.color = f.getColor();
        this.basePrice = f.getPrice();
        return this;
    }
    public BouquetBuilder flower(String flower){
        this.flower = flower;
        return this;
    }
    public BouquetBuilder color(String color){
        this.color = color;
        return this;
    }
     public BouquetBuilder wrap(String wrap){
        this.wrap = wrap;
        return this;
     }
     public BouquetBuilder card(String card){
        this.card = card;
        return this;
    }
    public BouquetBuilder basePrice(double amount){
        this.basePrice = Money.of(amount);
        return this;
    }
    public Bouquet build(){
        return new BouquetData(flower,color,wrap,card,basePrice);
    }
}