package facade;
import filials.BranchType;

public class OrderRequests {
    public String flower;
    public String color;
    public String wrap;
    public String card;
    public double basePrice;

    public boolean addBalloon, addCake, addChocoStrawberry, addFruitBasket, addToy;

    public java.time.LocalDate today;
    public java.time.LocalDate birthday;
    public String deliveryType;
    public int items;
    public String paymentMethod;

    public BranchType branchType;
    public Customer customer;
    public Address address;
}
