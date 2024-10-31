package br.com.desafio.domain.checkout.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Long.sum;

public class Order {

    private String id;
    private String customerId;
    private List<OrderItem> items;
    private Double total;

    public Order(String id, String customerId, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.total = this.total();
        this.validate();
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void validate() {
        if (this.id.isEmpty()) {
            throw new Error("Id is required");
        }
        if (this.customerId.isEmpty()) {
            throw new Error("Customer id is required");
        }
        if (this.items.isEmpty()) {
            throw new Error("Items are required");
        }
        if (this.items.stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new Error("Quantity must be greater than zero");
        }
    }

    public Double total() {
        return this.items.stream()
                .mapToDouble(OrderItem::total)
                .sum();
    }
}
