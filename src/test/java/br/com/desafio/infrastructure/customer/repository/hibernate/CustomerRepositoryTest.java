package br.com.desafio.infrastructure.customer.repository.hibernate;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerRepositoryTest {

    private EntityManagerFactory entityManagerFactory;

    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        customerRepository = new CustomerRepository(entityManagerFactory);
    }

    @AfterEach
    public void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    public void shouldCreateCustomer() throws ExecutionException, InterruptedException {
        Customer customer = new Customer(UUID.randomUUID().toString(), "Samn");
        Address address = new Address("Rua 2", 10, "64851-452", "Fortal");
        customer.changeAddress(address);
        customer.activate();

        customerRepository.create(customer);

        CompletableFuture<Customer> foundCustomer = customerRepository.find(customer.getId());

        assertNotNull(foundCustomer);
        assertEquals(foundCustomer.get().getId(), customer.getId());
    }
}
