package observer;

public class Event {
    public enum Type { STOCK, PRICE}
    private final Type type;
    private final String key;
    private final String data;

    public Event(Type type, String key, String data) {
        this.type = type;
        this.key = key;
        this.data = data;
    }
    public Type getType() {
        return type;
    }
    public String getKey() {
        return key;
    }
    public String getData() {
        return data;
    }
    @Override
    public String toString() {
        return type + ":" + key + ":" + data;
    }
}
