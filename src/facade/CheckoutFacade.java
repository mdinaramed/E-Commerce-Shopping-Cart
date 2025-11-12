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
import csv.*;
import java.io.IOException;

import java.time.LocalDate;
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
            System.out.println("Total: " + item.price().add(utilities.Money.of(deliveryFee)));

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

        try {
            InventoryCsvRepo inventoryRepo = new InventoryCsvRepo();

            if (requests.composite && requests.components != null && !requests.components.isEmpty()) {
                for (var e : requests.components.entrySet()) {
                    String flowerKey = e.getKey().toLowerCase();
                    int qty = e.getValue();
                    int reserved = inventoryRepo.tryReserve(requests.branchType, flowerKey, qty);
                    if (reserved < qty) {
                        System.out.println("Only " + reserved + " " + flowerKey + " left in stock. Adjusted automatically.");
                    }
                }
            } else {
            }
            CustomersCsvRepo customersRepo = new CustomersCsvRepo();
            int bonusEarned = (int) Math.round(total.getAmount() * 0.05);
            int newBalance = customersRepo.addPoints(
                    requests.customer.phone(),
                    requests.customer.name(),
                    bonusEarned
            );

            OrdersCsvRepo ordersRepo = new OrdersCsvRepo();
            ordersRepo.append(
                    requests.branchType.name(),
                    requests.customer.name(),
                    requests.customer.phone(),
                    requests.flower,
                    requests.items,
                    total.getAmount(),
                    bonusEarned,
                    requests.paymentMethod,
                    requests.deliveryType
            );

            System.out.println("Saved to system +" + bonusEarned + " bonus, new balance: " + newBalance);
        } catch (IOException e) {
            System.err.println("CSV save failed: " + e.getMessage());
        }

            Order earningOrder = new Order(
                    order.date,
                    order.birthday,
                    "pickup",
                    order.items,
                    order.customerId
            );
            Money earned = pricing.total(item, earningOrder);
            Bonus bonus = new Bonus();
            bonus.apply(earned, order);
            System.out.println("Bonus balance: " + Bonus.getBalance(String.valueOf(order.customerId)));

            interfaces.Delivery delivery = switch (requests.deliveryType.toLowerCase()) {
                case "pickup" -> new PickUp();
                default -> new Courier();
            };
            delivery.deliver(requests.customer.name(), requests.address.street());

            LocalTime expectedTime = switch (requests.deliveryType.toLowerCase()) {
                case "express" -> LocalTime.now().plusMinutes(30);
                case "courier" -> LocalTime.now().plusMinutes(80);
                case "pickup" -> LocalTime.now().plusMinutes(20);
                default -> LocalTime.now().plusMinutes(45);
            };
            System.out.println("Expected delivery time: ~" + expectedTime.truncatedTo(java.time.temporal.ChronoUnit.MINUTES));
        if (!requests.composite) {
            sklad.buy(requests.flower, Math.max(1, requests.items));
        }

            System.out.println("Order registered successfully");
            return OrderStatus.DELIVERED;
        }
    }
