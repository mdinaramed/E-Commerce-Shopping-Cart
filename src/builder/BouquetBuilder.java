package builder;
import interfaces.Bouquet;
import utilities.Money;

public class BouquetBuilder{
    private String flower;
    private String color;
    private String wrap;
    private String card;
    private Money basePrice;

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
  public BouquetBuilder basePrice(Money basePrice){
      this.basePrice = basePrice;
      return this;
  }
    public Bouquet build(){
        return new BouquetData(flower,color,wrap,card,basePrice);
    }
}