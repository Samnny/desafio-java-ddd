package br.com.desafio.usecase.product.list;

import java.util.List;

public record OutputListProductDto(
        List<Product> products
) {
    public record Product(String id, String name, double price) {}
}
