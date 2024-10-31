package br.com.desafio.domain.checkout.factory;

import br.com.desafio.domain.checkout.entity.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderFactoryTest {

    @Test
    public void shouldCreateOrder() {
        var orderProps = new OrderFactoryProps("1", "1",
                List.of(new OrderItem("1", "1", "Sabonete", 2.59, 1)));
        var order = OrderFactory.create(orderProps);

        assertEquals(order.getId(), orderProps.id());
        assertEquals(order.getCustomerId(), orderProps.customerId());
        assertEquals(order.getItems().size(), orderProps.items().size());
    }
}
