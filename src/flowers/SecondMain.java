package flowers;
import builder.ReadyMade;
import filials.BranchType;
import facade.Address;
import facade.Customer;
import facade.OrderRequests;
import factory.*;
import interfaces.Bouquet;
import interfaces.FlowerFactory;
import java.time.LocalDate;
import java.util.Scanner;

public class SecondMain {
    public static OrderRequests startInteractive() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n ✨Welcome to Ademi Flowers✨");
        System.out.println("Please choose branch:");
        int i = 1;
        for (BranchType bt : BranchType.values()) {
            System.out.println(i++ + ". " + bt.name().replace("_", " "));
        }
        System.out.print("Choose branch: ");
        int branchChoice = Integer.parseInt(scanner.nextLine());
        BranchType branch = BranchType.values()[branchChoice - 1];

        System.out.println("\nDo you want:");
        System.out.println("1) Build your own bouquet 💐");
        System.out.println("2) Choose from ready-made bouquets 🎁");
        System.out.print("Your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        String flowerType;
        String color;
        String wrap;
        String card;
        double basePrice;

        if (choice == 2) {
            System.out.println("\nAvailable Ready Bouquets:");
            System.out.println("1) 25 Red Roses - 25,000 KZT");
            System.out.println("2) 51 Red Roses - 50,000 KZT");
            System.out.println("3) 101 Roses Mix - 110,000 KZT");
            System.out.println("4) Spring Mix (Lily + Orchid + Chrysanthemum) - 26,000 KZT");
            System.out.println("5) Royal Mix (Hydrangea + Orchid) - 34,500 KZT");
            System.out.print("Choose: ");
            int ready = Integer.parseInt(scanner.nextLine());

            Bouquet selected = switch (ready) {
                case 1 -> ReadyMade.redRoses25();
                case 2 -> ReadyMade.redRoses51();
                case 3 -> ReadyMade.rosesMix101();
                case 4 -> ReadyMade.springMix();
                case 5 -> ReadyMade.royalMix();
                default -> ReadyMade.redRoses25();
            };

            flowerType = selected.flower();
            color = selected.color();
            wrap = selected.wrap();
            card = selected.card();
            basePrice = selected.basePrice().getAmount();

            System.out.println("\nYou chose a ready-made bouquet:");
            System.out.println(selected);

        } else {
            System.out.println("\nFlower List: rose, tulips, peony, lily, orchid, hydrangea, chrysanthemum");
            System.out.print("Flower type: ");
            flowerType = scanner.nextLine().trim();

            System.out.print("Flower color: ");
            color = scanner.nextLine().trim();

            System.out.print("Wrap (Classic / Craft / Silk / Premium): ");
            wrap = scanner.nextLine().trim();

            System.out.print("Card (write a short note or No): ");
            card = scanner.nextLine().trim();

            FlowerFactory flowerFactory = switch (flowerType.toLowerCase()) {
                case "rose" -> new Rose();
                case "tulips" -> new Tulips();
                case "peony" -> new Peony();
                case "lily" -> new Lily();
                case "orchid" -> new Orchid();
                case "hydrangea" -> new Hydrangea();
                case "chrysanthemum" -> new Chrysanthemum();
                default -> throw new IllegalArgumentException("Unknown flower type: " + flowerType);
            };
            Flower flower = flowerFactory.collectFlower(color);
            basePrice = flower.getPrice().getAmount();

            System.out.println("\n Selected flower: " + flower.getName() + " (" + flower.getColor() + ")");
            System.out.println(" Price: " + flower.getPrice());
        }
        System.out.print("\nHow many bouquets would you like to order? ");
        int items = Integer.parseInt(scanner.nextLine());

        System.out.println("\nWould you like to add any extras?");
        boolean addBalloon = ask(scanner, "Balloon");
        boolean addCake = ask(scanner, "Cake");
        boolean addToy = ask(scanner, "Toy");
        boolean addChocoStrawberry = ask(scanner, "Choco Strawberries");
        boolean addFruitBasket = ask(scanner, "Fruit Basket");

        System.out.print("\nDelivery (pickup / courier / express): ");
        String delivery = scanner.nextLine().trim();

        System.out.print("Payment (Kaspi / Halyk / Freedom / ApplePay): ");
        String payment = scanner.nextLine().trim();

        System.out.print("\nYour name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Your phone number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Your address: ");
        String addressLine = scanner.nextLine().trim();

        System.out.print("\nEnter your birthday (YYYY-MM-DD) or press Enter to skip: ");
        String birthdayInput = scanner.nextLine().trim();
        LocalDate birthday = birthdayInput.isEmpty() ? null : LocalDate.parse(birthdayInput);

        OrderRequests req = new OrderRequests();
        req.branchType = branch;
        req.customer = new Customer(name, phone);
        req.address = new Address(addressLine);

        req.flower = flowerType;
        req.color = color;
        req.wrap = wrap;
        req.card = card;
        req.basePrice = basePrice;

        req.addBalloon = addBalloon;
        req.addCake = addCake;
        req.addToy = addToy;
        req.addChocoStrawberry = addChocoStrawberry;
        req.addFruitBasket = addFruitBasket;

        req.deliveryType = delivery;
        req.paymentMethod = payment;
        req.today = LocalDate.now();
        req.birthday = birthday;
        req.items = items;

        System.out.println("\nYour order request is ready.Sending to thesystem...\n");
        return req;
    }

    private static boolean ask(Scanner scanner, String option) {
        System.out.print("Add " + option + "? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        return ans.startsWith("y");
    }
}