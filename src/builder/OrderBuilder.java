package builder;

import strategy.Order;

import java.time.LocalDate;

public class OrderBuilder {
    private LocalDate date;
    private LocalDate birthday;
    private String deliveryType;
    private int items;
    private String customerId;

    public OrderBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }
    public OrderBuilder birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }
    public OrderBuilder deliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }
    public OrderBuilder items(int items) {
        this.items = items;
        return this;
    }
    public OrderBuilder customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }
    public Order build() {
        Order order = new Order(date, birthday, deliveryType, items, customerId);
        return order;
    }
}
