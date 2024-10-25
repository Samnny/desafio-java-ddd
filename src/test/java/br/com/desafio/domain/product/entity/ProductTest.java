package br.com.desafio.domain.product.entity;

import br.com.desafio.domain.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductTest {

    @Test
    public void shouldThrowErrorWhenIdIsEmpty() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("", "Product", 10.0);
        });
        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenNameIsEmpty() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("123", "", 10.0);
        });
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenPriceIsLessThanZero() {
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            new Product("123", "Product", 0.0);
        });
        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    public void shouldChangeName() {
        var product = new Product("123", "Product", 10.0);
        product.changeName("Product updated");
        assertEquals("Product updated", product.getName());
    }

    @Test
    public void shouldChangePrice() {
        var product = new Product("123", "Product", 10.0);
        product.changePrice(20.0);
        assertEquals(20.0, product.getPrice());
    }
}
