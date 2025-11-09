package observer;
import java.util.HashMap;
import java.util.Map;

public class PriceCatalog implements Observer {
    private final Map<String, Integer> lastQuantity = new HashMap<>();
    private final Map<String, Long> lastPrice = new HashMap<>();

    @Override
    public void onEvent(Event event) {
        switch (event.getType()) {
            case STOCK -> {
                int index = event.getData().indexOf('=');
                int quantity = (index > 0) ? Integer.parseInt(event.getData().substring(index + 1)): 0;
                lastQuantity.put(event.getKey(), quantity);
                System.out.println("Stock" + event.getKey() + ":" + quantity + " pieces");
            }
            case PRICE -> {
                int index = event.getData().indexOf('=');
                int price = (index > 0) ? Integer.parseInt(event.getData().substring(index + 1)): 0;
                lastPrice.put(event.getKey(), (long) price);
                System.out.println("Price" + event.getKey() + ":" + price + " pieces");
            }
        }
    }
}
