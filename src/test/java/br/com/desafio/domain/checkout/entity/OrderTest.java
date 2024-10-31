package br.com.desafio.domain.checkout.entity;

import br.com.desafio.domain.customer.entity.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    @Test
    public void shouldThrowErrorWhenIdIsEmpty() {
        Error exception = assertThrows(Error.class, () -> {
            new Order("", "1", List.of());
        });
        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenCustomerIdIsEmpty() {
        Error exception = assertThrows(Error.class, () -> {
            new Order("1", "", List.of());
        });
        assertEquals("Customer id is required", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenItemsIsEmpty() {
        Error exception = assertThrows(Error.class, () -> {
            new Order("1", "1", List.of());
        });
        assertEquals("Items are required", exception.getMessage());
    }

    @Test
    public void shouldCalculateTotal() {
        var item = new OrderItem("1", "1", "Sabonete", 2.59, 2);
        var item2 = new OrderItem("2", "2", "Sabonete Clicerina", 4.59, 2);
        var order = new Order("1", "1", List.of(item));
        var total = order.total();

        assertEquals(total, 5.18);

        var order2 = new Order("1", "1", List.of(item, item2));
        total = order2.total();

        assertEquals(total, 14.36);
    }

    @Test
    public void shouldThrowErrorIfTheItemQteIsLessOrEqualZero() {
        Error exception = assertThrows(Error.class, () -> {
            var item = new OrderItem("1", "1", "Sabonete", 2.59, 0);
            var order = new Order("1", "1", List.of(item));
        });
        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }
}