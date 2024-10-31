package br.com.desafio.domain.checkout.service;

import br.com.desafio.domain.checkout.entity.Order;
import br.com.desafio.domain.checkout.entity.OrderItem;
import br.com.desafio.domain.customer.entity.Customer;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    @Test
    public void shouldPlaceAnOrder() {
        var customer = new Customer("1", "Customer 1");
        var item = new OrderItem("1", "Item 1", "product", 10.0, 1);
        var order = OrderService.placeOrder(customer, List.of(item));

        assertEquals(customer.getRewardPoints(), 5);
        assertEquals(order.total(), 10);
    }

    @Test
    public void shouldGetTotalOfAllOrders() {
        var item = new OrderItem("1", "Item 1", "product", 10.0, 1);
        var item2 = new OrderItem("2", "Item 2", "product", 20.0, 2);
        var order = new Order("1", "1", List.of(item));
        var order2 = new Order("2", "2", List.of(item2));

        var total = OrderService.total(List.of(order,order2));
        assertEquals(total, 50);
    }
}
