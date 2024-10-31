package br.com.desafio.domain.checkout.service;

import br.com.desafio.domain.checkout.entity.Order;
import br.com.desafio.domain.checkout.entity.OrderItem;
import br.com.desafio.domain.customer.entity.Customer;

import java.util.List;
import java.util.UUID;

public class OrderService {
    public static Order placeOrder(Customer customer, List<OrderItem> items) {
        if(items.isEmpty()) {
            throw new Error("Order must have at least one item");
        }
        Order order = new Order(UUID.randomUUID().toString(), customer.getId(), items);
        var total = (int) (order.total()/2);
        customer.addRewardPoints(total);
        return order;
    }

    public static Double total(List<Order> orders) {
        return orders.stream().mapToDouble(Order::total).sum();
    }
}
