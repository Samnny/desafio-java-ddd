package br.com.desafio.domain.product.factory;

import br.com.desafio.domain.product.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductFactoryTest {

    @Test
    public void shouldCreateProduct() {
        Product productFactory = ProductFactory.create("Product", 10.0);

        assertNotNull(productFactory.getId());
        assertEquals("Product", productFactory.getName());
        assertEquals(10.0, productFactory.getPrice());
    }
}
