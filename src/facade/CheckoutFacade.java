package facade;

import utilities.Money;
import filials.*;
import interfaces.*;
import decorator.*;
import strategy.*;
import adapter.*;
import observer.Sklad;
import strategy.Delivery;
import csv.*;
import builder.OrderBuilder;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.LocalTime;

public class CheckoutFacade {
    private final BranchManager branches;
    public CheckoutFacade(BranchManager branches) {
        this.branches = branches;
    }
    public OrderStatus placeOrder(OrderRequests requests) {
        Branch branch = branches.getBranch(requests.branchType);
        Sklad sklad = branch.getSklad();
        System.out.println("Registering the order in the filial: " + requests.branchType.name().replace("_", " "));

        String lf = requests.flower == null ? "" : requests.flower.trim();
        boolean composite = requests.basePrice > 0 && (lf.contains("+") || lf.toLowerCase().contains(" x"));

        Bouquet bouquet;
        if (composite) {
            bouquet = new builder.BouquetBuilder()
                    .flower(requests.flower)
                    .color(requests.color)
                    .wrap(requests.wrap)
                    .card(requests.card)
                    .basePrice(requests.basePrice)
                    .build();
        } else {
            FlowerFactory flowerFactory = switch (requests.flower.trim().toLowerCase()) {
                case "chrysanthemum" -> new factory.Chrysanthemum();
                case "hydrangea" -> new factory.Hydrangea();
                case "lily" -> new factory.Lily();
                case "orchid" -> new factory.Orchid();
                case "peony" -> new factory.Peony();
                case "rose" -> new factory.Rose();
                case "tulips" -> new factory.Tulips();
                default -> throw new IllegalArgumentException("Invalid flower type: " + requests.flower);
            };
            bouquet = new builder.BouquetBuilder()
                    .fromFactory(flowerFactory, requests.color)
                    .wrap(requests.wrap)
                    .card(requests.card)
                    .basePrice(requests.basePrice)
                    .build();
        }

        System.out.println("Bouquet: " + bouquet);

        PricedItem item = new BasicBouquet(bouquet);
        if (requests.addBalloon) item = new Balloon(item);
        if (requests.addCake) item = new Cake(item);
        if (requests.addChocoStrawberry) item = new ChocoStrawberry(item);
        if (requests.addFruitBasket) item = new FruitBasket(item);
        if (requests.addToy) item = new Toy(item);

        System.out.println("\nOrder details:");
        System.out.println(item.breakdown());
        double deliveryFee = switch (requests.deliveryType.toLowerCase()) {
            case "express" -> 2500;
            case "courier" -> 1500;
            default -> 0;
        };
        if (deliveryFee > 0) {
            System.out.println((" + Delivery (" + requests.deliveryType + ") +" + deliveryFee + " KZT"));
        }
        System.out.println("_________________________________");
        System.out.println("Subtotal: " + requests.basePrice + " KZT");

        Order order = new OrderBuilder()
                .date(requests.today)
                .birthday(requests.birthday)
                .deliveryType(requests.deliveryType)
                .items(requests.items)
                .customerId((requests.customerId != null && !requests.customerId.isBlank()) ? requests.customerId : requests.customer.phone())
                .build();

        Pricing pricing = new PriceCalculator(
                List.of(
                        new BirthdayDiscount(),
                        new BulkDiscount(),
                        new WomenDayDiscount()),
                new Delivery());

        Money total = pricing.total(item, order);
        System.out.println("Total price: " + total + " KZT");

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

        new CsvOrderService().saveOrderData(requests, total);

        interfaces.Delivery delivery = switch (requests.deliveryType.toLowerCase()) {
            case "pickup" -> new PickUp();
            default -> new Courier();
        };
        System.out.println();
        delivery.deliver(requests.customer.name(), requests.address.street());

        LocalTime expectedTime = switch (requests.deliveryType.toLowerCase()) {
            case "express" -> LocalTime.now().plusMinutes(30);
            case "courier" -> LocalTime.now().plusMinutes(80);
            case "pickup" -> LocalTime.now().plusMinutes(20);
            default -> LocalTime.now().plusMinutes(45);
        };

        String branchName = requests.branchType.name().replace("_", " ");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");

        if (requests.deliveryType.equalsIgnoreCase("pickup")) {
            System.out.println("We'll prepare your bouquet at " + branchName + " ~" + expectedTime.format(fmt));
        } else if (requests.deliveryType.equalsIgnoreCase("express")) {
            System.out.println("Expected delivery time: ~" + expectedTime.format(fmt));
        } else if (requests.deliveryType.equalsIgnoreCase("courier")) {
            System.out.println("Expected delivery time: ~" + expectedTime.format(fmt));
        } else {
            System.out.println("Estimated delivery time: ~" + expectedTime.format(fmt));
        }

        if (!requests.composite) {
            sklad.buy(requests.flower, Math.max(1, requests.items));
        }

        System.out.println("Order registered successfully");
        return OrderStatus.DELIVERED;
    }
}