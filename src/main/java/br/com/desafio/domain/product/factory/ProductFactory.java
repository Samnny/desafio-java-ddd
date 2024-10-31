package br.com.desafio.domain.product.factory;

import br.com.desafio.domain.product.entity.Product;

import java.util.UUID;

public class ProductFactory {

    public static Product create(String name, Double price) {
        return new Product(UUID.randomUUID().toString(), name, price);
    }
}
