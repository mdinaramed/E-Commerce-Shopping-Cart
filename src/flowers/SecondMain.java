package flowers;

import builder.ReadyMade;
import filials.*;
import facade.*;
import factory.*;
import interfaces.Bouquet;
import interfaces.FlowerFactory;
import observer.Sklad;
import builder.BouquetBuilder;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import csv.InventoryCsvRepo;

public class SecondMain {
    public static OrderRequests startInteractive(BranchManager branches) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n✨WELCOME TO ADEMI ✨");
        System.out.println("Please choose branch:");
        int i = 1;
        for (BranchType bt : BranchType.values()) {
            System.out.println(i++ + ". " + bt.name().replace("_", " "));
        }
        System.out.print("Choose branch: ");
        int branchChoice = readInt(sc, 1, BranchType.values().length);
        BranchType branchType = BranchType.values()[branchChoice - 1];

        Branch branch = branches.getBranch(branchType);
        Sklad sklad = branch.getSklad();
        InventoryCsvRepo inv = new InventoryCsvRepo();

        System.out.println("\nDo you want:");
        System.out.println("1) Collect bouquet 💐");
        System.out.println("2) Choose from ready-made bouquets 🎁");
        System.out.print("Your choice: ");
        int choice = readInt(sc, 1, 2);

        String flowerLabel, colorLabel, wrap, card;
        double basePricePerBouquet;
        int items;
        boolean addBalloon, addCake, addToy, addChocoStrawberry, addFruitBasket;
        String delivery, payment, name, phone, addressLine;
        LocalDate birthday;

        Map<String, Integer> comps;
        if (choice == 1) {
            comps = new LinkedHashMap<>();
            List<Component> components = new ArrayList<>();
            System.out.println("\nFlower List: rose, tulips, peony, lily, orchid, hydrangea, chrysanthemum");

            while (true) {
                System.out.print("Enter flower type (or press Enter to finish): ");
                String type = sc.nextLine().trim();
                if (type.isEmpty()) break;
                String base = type.toLowerCase();

                System.out.print("Flower color: ");
                String col = sc.nextLine().trim();

                System.out.print("How many stems of " + type + "? ");
                int qtyPerBouquet = readPositiveInt(sc);

                int have = inv.previewQty(branchType, base);
                if (have < qtyPerBouquet) {
                    System.out.println("Only " + have + " " + type + " left in stock for now.");
                    System.out.print("Take " + have + " instead? (y/n): ");
                    if (yes(sc)) qtyPerBouquet = have;
                    else {
                        System.out.println("Skipped " + type + ".");
                        continue;
                    }
                }

                FlowerFactory ff = factoryOf(type);
                factory.Flower f = ff.collectFlower(col);
                components.add(new Component(f, qtyPerBouquet));
                comps.put(base, comps.getOrDefault(base, 0) + qtyPerBouquet);

                double part = f.getPrice().getAmount() * qtyPerBouquet;
                System.out.println("✅ Added " + qtyPerBouquet + " × " + f.getName() + "[" + col + "] — " + (long) part + " KZT\n");
            }

            if (components.isEmpty()) {
                System.out.println("No flowers selected. Cancelling.");
                return null;
            }

            basePricePerBouquet = components.stream()
                    .mapToDouble(c -> c.flower().getPrice().getAmount() * c.qty()).sum();

            flowerLabel = components.stream()
                    .map(c -> c.flower().getName() + " x" + c.qty())
                    .collect(Collectors.joining(" + "));

            Set<String> colors = components.stream().map(c -> c.flower().getColor()).collect(Collectors.toSet());
            colorLabel = (colors.size() == 1) ? colors.iterator().next() : "Mixed";

            System.out.print("Wrap (Classic / Craft / Silk / Premium): ");
            wrap = sc.nextLine().trim();

            System.out.print("Card (write a short note or No): ");
            card = sc.nextLine().trim();

            System.out.print("\nHow many bouquets would you like to order? ");
            items = readPositiveInt(sc);

            for (Component c : components) {
                String base = c.flower().getName().toLowerCase();
                int need = c.qty() * items;
                int have = inv.previewQty(branchType, base);
                if (have < need) {
                    System.out.println("For " + base + " you need " + need + ", but only " + have + " in stock.");
                    int maxBouquets = (c.qty() == 0) ? 0 : (have / c.qty());
                    System.out.print("Make " + maxBouquets + " bouquets instead? (y/n): ");
                    if (yes(sc)) {
                        items = maxBouquets;
                    }
                    else {
                        System.out.println("Order cancelled due to insufficient stock.");
                        return null;
                    }
                }
            }
            Map<String, Integer> multiplied = new LinkedHashMap<>();
            for (var e : comps.entrySet()) {
                multiplied.put(e.getKey(), e.getValue() * items);
            }
            comps = multiplied;

            System.out.println("\nBase price per bouquet: " + (long) basePricePerBouquet + " KZT");
            System.out.println("Composition: " + flowerLabel + " | colors: " + colorLabel);

            System.out.println("\nWould you like to add any extras?");
            addBalloon = ask(sc, "Balloon");
            addCake = ask(sc, "Cake");
            addToy = ask(sc, "Toy");
            addChocoStrawberry = ask(sc, "Choco Strawberries");
            addFruitBasket = ask(sc, "Fruit Basket");

            System.out.print("\nDelivery (pickup / courier / express): ");
            delivery = sc.nextLine().trim();
            if (delivery.equalsIgnoreCase("pickup")) {
                addressLine = "Pickup at branch: " + branchType.name().replace("_", " ");
            } else {
                System.out.print("Address for delivery: ");
                addressLine = sc.nextLine().trim();
            }

            System.out.print("Payment method (Kaspi / Halyk / Freedom / ApplePay): ");
            payment = sc.nextLine().trim();

            System.out.print("\nYour name: ");
            name = sc.nextLine().trim();
            System.out.print("Your phone number: ");
            phone = sc.nextLine().trim();

            System.out.print("Enter your birthday (YYYY-MM-DD) or press Enter to skip: ");
            String b = sc.nextLine().trim();
            birthday = (b.isEmpty() ? null : LocalDate.parse(b));

        } else {
            System.out.println("\nAvailable Ready Bouquets:");
            System.out.println("1) 25 Red Roses - 25,000 KZT");
            System.out.println("2) 51 Red Roses - 50,000 KZT");
            System.out.println("3) 101 Roses Mix - 110,000 KZT");
            System.out.println("4) Spring Mix (Lily + Orchid + Chrysanthemum) - 26,000 KZT");
            System.out.println("5) Royal Mix (Hydrangea + Orchid) - 34,500 KZT");
            System.out.print("Choose: ");
            int ready = readInt(sc, 1, 5);

            Bouquet selected = switch (ready) {
                case 1 -> ReadyMade.redRoses25();
                case 2 -> ReadyMade.redRoses51();
                case 3 -> ReadyMade.rosesMix101();
                case 4 -> ReadyMade.springMix();
                case 5 -> ReadyMade.royalMix();
                default -> ReadyMade.redRoses25();
            };

            System.out.println("\nYou chose a ready-made bouquet:");
            System.out.println(selected);

            flowerLabel = selected.flower();
            colorLabel = selected.color();
            wrap = selected.wrap();
            card = selected.card();
            basePricePerBouquet = selected.basePrice().getAmount();

            System.out.print("\nHow many bouquets would you like to order? ");
            items = readPositiveInt(sc);

            System.out.println("\nWould you like to add any extras?");
            addBalloon = ask(sc, "Balloon");
            addCake = ask(sc, "Cake");
            addToy = ask(sc, "Toy");
            addChocoStrawberry = ask(sc, "Choco Strawberries");
            addFruitBasket = ask(sc, "Fruit Basket");

            System.out.print("\nDelivery (pickup / courier / express): ");
            delivery = sc.nextLine().trim();
            if (delivery.equalsIgnoreCase("pickup")) {
                addressLine = "Pickup at branch: " + branchType.name().replace("_", " ");
            } else {
                System.out.print("Address for delivery: ");
                addressLine = sc.nextLine().trim();
            }
            System.out.print("Payment method (Kaspi / Halyk / Freedom / ApplePay): ");
            payment = sc.nextLine().trim();

            System.out.print("\nYour name: ");
            name = sc.nextLine().trim();
            System.out.print("Your phone number: ");
            phone = sc.nextLine().trim();

            System.out.print("Enter your birthday (YYYY-MM-DD) or press Enter to skip: ");
            String b = sc.nextLine().trim();
            birthday = b.isEmpty() ? null : LocalDate.parse(b);
            comps = Collections.emptyMap();
        }

        Bouquet bouquet = new BouquetBuilder()
                .flower(flowerLabel)
                .color(colorLabel)
                .wrap(wrap)
                .card(card)
                .basePrice(basePricePerBouquet)
                .build();

        OrderRequests rq = new OrderRequests();
        rq.branchType = branchType;
        rq.customer = new Customer(name, phone);
        rq.address  = new Address(addressLine);

        rq.flower = bouquet.flower();
        rq.color = bouquet.color();
        rq.wrap = bouquet.wrap();
        rq.card = bouquet.card();
        rq.basePrice = basePricePerBouquet;

        rq.addBalloon = addBalloon;
        rq.addCake = addCake;
        rq.addToy = addToy;
        rq.addChocoStrawberry = addChocoStrawberry;
        rq.addFruitBasket = addFruitBasket;

        rq.deliveryType = delivery;
        rq.paymentMethod = payment;

        rq.today = LocalDate.now();
        rq.birthday = birthday;
        rq.items = items;
        rq.composite = (choice == 1);
        rq.components = comps;

        System.out.println("\nYour order request is ready. Sending to the system...\n");
        return rq;
    }

    private static boolean ask(Scanner sc, String label) {
        System.out.print("Add " + label + "? (y/n): ");
        return yes(sc);
    }
    private static boolean yes(Scanner sc) {
        String ans = sc.nextLine().trim().toLowerCase();
        return ans.startsWith("y") || ans.startsWith("д");
    }
    private static int readInt(Scanner sc, int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.print("Enter a number from " + min + " to " + max + ": ");
        }
    }
    private static int readPositiveInt(Scanner sc) {
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v > 0) return v;
            } catch (Exception ignored) {}
            System.out.print("Enter a positive number: ");
        }
    }
    private static FlowerFactory factoryOf(String type) {
        switch (type.toLowerCase()) {
            case "rose": return new Rose();
            case "tulips": return new Tulips();
            case "peony": return new Peony();
            case "lily": return new Lily();
            case "orchid": return new Orchid();
            case "hydrangea": return new Hydrangea();
            case "chrysanthemum": return new Chrysanthemum();
            default: throw new IllegalArgumentException("Unknown flower type: " + type);
        }
    }
    private record Component(factory.Flower flower, int qty) {}
}