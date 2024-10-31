package br.com.desafio.domain.checkout.factory;

import br.com.desafio.domain.checkout.entity.OrderItem;

import java.util.List;

public record OrderFactoryProps(
        String id,
        String customerId,
        List<OrderItem> items
) {}
