package br.com.desafio.domain.product.service;

import br.com.desafio.domain.product.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    @Test
    public void shouldChangeThePricesOfAllProducts() {

        Product product = new Product("1", "Product", 10.0);
        Product product2 = new Product("2", "Product", 11.0);

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);

        ProductService.increasePrice(products, 100);
        assertEquals(20.0, product.getPrice());
        assertEquals(22.0, product2.getPrice());
    }
}
