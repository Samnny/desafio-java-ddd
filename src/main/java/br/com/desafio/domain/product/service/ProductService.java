package br.com.desafio.domain.product.service;

import br.com.desafio.domain.product.entity.Product;

import java.util.List;

public class ProductService {

    public static List<Product> increasePrice(List<Product> products, Integer percentage) {
        products.forEach(product -> {
            product.changePrice((product.getPrice() * percentage)/100 + product.getPrice());
        });
        return products;
    }
}
