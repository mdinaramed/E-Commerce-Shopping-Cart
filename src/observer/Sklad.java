package observer;
import java.util.*;

public class Sklad implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private final Map<String, Integer> stock = new HashMap<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer observer : observers) observer.onEvent(event);
    }

    public int getQuantity(String flower) {
        return stock.getOrDefault(flower, 0);
    }
    public void setQuantity(String flower, int quantity) {
        stock.put(flower, quantity);
        notifyObservers(new Event(Event.Type.STOCK,flower, "quantity: " + getQuantity(flower)));
    }
    public void add(String flower, int quantity) {
        setQuantity(flower, getQuantity(flower) + quantity);
    }
    public int buy(String flower, int quantity) {
        int have = getQuantity(flower);
        int bought = Math.min(getQuantity(flower), Math.max(have,0));
        setQuantity(flower, have-bought);
        return bought;
    }
    public void changedPrice(String flower, double newPrice) {
        notifyObservers(new Event(Event.Type.PRICE,flower, "price: " + (long)newPrice));
    }
}
