package csv;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class OrdersCsvRepo {
    private static final String HEADER = "order_id,datetime,branch,customer_name,phone,flower,qty,total,bonus_earned,payment,delivery_type";
    private final Path path = Path.of("data", "orders.csv");

    public synchronized void append(String branch, String customerName, String phone, String flower, int qty, double total, int bonusEarned, String payment, String deliveryType) throws IOException {
        Csv.append(path, HEADER, new String[]{
                UUID.randomUUID().toString(),
                Csv.now(),
                branch,
                customerName,
                phone,
                flower,
                String.valueOf(qty),
                String.valueOf(total),
                String.valueOf(bonusEarned),
                payment,
                deliveryType
        });
    }
}