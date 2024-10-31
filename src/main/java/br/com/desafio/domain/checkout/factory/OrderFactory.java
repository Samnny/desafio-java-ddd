package br.com.desafio.domain.checkout.factory;

import br.com.desafio.domain.checkout.entity.Order;
import br.com.desafio.domain.checkout.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderFactory {

    public static Order create(OrderFactoryProps props) {
        List<OrderItem> items = props.items().stream().map(item ->
                new OrderItem(
                        item.getId(),
                        item.getProductId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity()
                )).collect(Collectors.toList());
        return new Order(props.id(), props.customerId(), items);
    }
}
