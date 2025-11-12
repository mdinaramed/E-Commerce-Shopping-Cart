package facade;
import bonus.Bonus;
import utilities.Money;
import filials.*;
import builder.BouquetBuilder;
import interfaces.*;
import decorator.*;
import interfaces.Payment;
import strategy.*;
import adapter.*;
import observer.Sklad;
import strategy.Delivery;
import java.util.List;

public class CheckoutFacade {
    private final BranchManager branches;

    public CheckoutFacade(BranchManager branches) {
        this.branches = branches;
    }
    public OrderStatus placeOrder(OrderRequests requests) {
        Branch branch = branches.getBranch(requests.branchType);
        Sklad sklad = branch.getSklad();
        System.out.println("Registering the order in the filial: " + requests.branchType.name().replace("_"," "));

        FlowerFactory flowerFactory = switch (requests.flower.trim().toLowerCase()){
            case "chrysanthemum" -> new factory.Chrysanthemum();
            case "hydrangea" -> new factory.Hydrangea();
            case "lily" -> new factory.Lily();
            case "orchid" -> new factory.Orchid();
            case "peony" -> new factory.Peony();
            case "rose" -> new factory.Rose();
            case "tulips" -> new factory.Tulips();
            default -> throw new IllegalArgumentException("Invalid flower type: " + requests.flower);
        };

        Bouquet bouquet = new BouquetBuilder()
                .fromFactory(flowerFactory, requests.color)
                .wrap(requests.wrap)
                .card(requests.card)
                .basePrice(requests.basePrice)
                .build();
        System.out.println("Bouquet: " + bouquet);

        PricedItem item = new BasicBouquet(bouquet);
        if (requests.addBalloon) item = new Balloon(item);
        if (requests.addCake) item = new Cake(item);
        if (requests.addChocoStrawberry) item = new ChocoStrawberry(item);
        if (requests.addFruitBasket) item = new FruitBasket(item);
        if (requests.addToy) item = new Toy(item);
        System.out.println("Selected item: " + item.title());
        System.out.println("Added " + item.price());

        Order order = new Order(requests.today, requests.birthday, requests.deliveryType, requests.items, requests.customerId);

        Pricing pricing = new PriceCalculator(
                List.of(
                        new BirthdayDiscount(),
                        new BulkDiscount(),
                        new WomenDayDiscount()),
                new Delivery());
        Money total = pricing.total(item, order);
        System.out.println("Total price: " + total);

        Payment payment = switch (requests.paymentMethod.toLowerCase()) {
            case "kaspi" -> new Kaspi();
            case "halyk" -> new Halyk();
            case "freedom" -> new Freedom();
            case "applepay" -> new ApplePay();
            default -> {
                System.out.println("Invalid payment method " + requests.paymentMethod + "Use kaspi by default");
                yield new Kaspi();
            }
        };

        boolean paid = payment.pay(requests.customer.name(), total);
        if (!paid) {
            System.out.println("Payment failed");
            return OrderStatus.CREATED;
        }
        System.out.println("Payment successful");
        Order earningOrder = new Order (
                order.date,
                order.birthday,
                "pickup",
                order.items,
                order.customerId
        );
        Money earned = pricing.total(item, earningOrder);

        Bonus bonus =  new Bonus();
        bonus.apply(earned, order);
        System.out.println("Bonus balance: " + Bonus.getBalance(String.valueOf(order.customerId)));

        interfaces.Delivery delivery = switch (requests.deliveryType.toLowerCase()){
            case "pickup" -> new PickUp();
            default -> new Courier();
        };
        delivery.deliver(requests.customer.name(), requests.address.street());

        sklad.buy(requests.flower, Math.max(1, requests.items));

        System.out.println("Order registered successfully");
        return OrderStatus.DELIVERED;

    }
}
