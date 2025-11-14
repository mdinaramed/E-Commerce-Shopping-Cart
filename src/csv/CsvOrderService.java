package csv;

import facade.OrderRequests;
import utilities.Money;
import java.io.IOException;
import observer.Sklad;

public class CsvOrderService {
    public void saveOrderData(OrderRequests requests, Money total) {
        try {
            InventoryCsvRepo inventoryRepo = new InventoryCsvRepo();
            if (requests.composite && requests.components != null
                    && !requests.components.isEmpty()) {
                for (var e : requests.components.entrySet()) {
                    String flowerKey = e.getKey().toLowerCase();
                    int qty = e.getValue();
                    int reserved = inventoryRepo.tryReserve(
                            requests.branchType,
                            flowerKey,
                            qty
                    );
                    if (reserved < qty) {
                        System.out.println(
                                "Only " + reserved + " " + flowerKey + " left in stock. Adjusted automatically."
                        );
                    }
                }
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
            System.out.println();
            System.out.println("Saved to system +" + bonusEarned + " bonus, new balance: " + newBalance);

        } catch (IOException e) {
            System.err.println("CSV saving failed: " + e.getMessage());
        }
    }
}