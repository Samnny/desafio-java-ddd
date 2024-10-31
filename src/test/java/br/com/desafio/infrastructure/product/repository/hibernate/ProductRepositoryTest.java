package br.com.desafio.infrastructure.product.repository.hibernate;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductRepositoryTest {

    private EntityManagerFactory entityManagerFactory;

    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        productRepository = new ProductRepository(entityManagerFactory);
    }

    @AfterEach
    public void tearDown() {
        entityManagerFactory.close();
        productRepository.close();
    }

    @Test
    public void shouldCreateProduct() {
        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.59);

        productRepository.create(product);

        CompletableFuture<Product> foundProduct = productRepository.find(product.getId());

        assertEquals(foundProduct.join().getId(), product.getId());
        assertEquals(foundProduct.join().getName(), product.getName());
        assertEquals(foundProduct.join().getPrice(), product.getPrice());
    }

    @Test
    public void shouldUpdateProduct() {
        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.59);
        productRepository.create(product);

        product.changeName("Sabonete glicerina");
        product.changePrice(5.29);
        productRepository.update(product);

        CompletableFuture<Product> foundProduct = productRepository.find(product.getId());

        assertEquals(foundProduct.join().getName(), product.getName());
        assertEquals(foundProduct.join().getPrice(), product.getPrice());
    }

    @Test
    public void shouldFindProduct() {
        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.59);
        productRepository.create(product);

        CompletableFuture<Product> foundProduct = productRepository.find(product.getId());

        assertEquals(foundProduct.join().getId(), product.getId());
        assertEquals(foundProduct.join().getName(), product.getName());
        assertEquals(foundProduct.join().getPrice(), product.getPrice());
    }

    @Test
    public void shouldFindAllProducts() {
        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.59);
        Product product2 = new Product(UUID.randomUUID().toString(), "Sabonete clicerina", 5.38);
        productRepository.create(product);
        productRepository.create(product2);

        CompletableFuture<Product[]> foundProduct = productRepository.findAll();

        assertEquals(foundProduct.join().length, 2);
        assertEquals(foundProduct.join()[0].getName(), product.getName());
        assertEquals(foundProduct.join()[1].getName(), product2.getName());
    }
}
