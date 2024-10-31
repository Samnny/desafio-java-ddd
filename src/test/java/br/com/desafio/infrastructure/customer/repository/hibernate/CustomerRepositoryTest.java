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
        customerRepository.close();
        entityManagerFactory.close();
    }

    @Test
    public void shouldCreateCustomer() throws ExecutionException, InterruptedException {
        Customer customer = createCustomer();

        customerRepository.create(customer);

        CompletableFuture<Customer> foundCustomer = customerRepository.find(customer.getId());

        assertNotNull(foundCustomer);
        assertEquals(foundCustomer.get().getId(), customer.getId());
    }

    @Test
    public void shouldUpdateCustomer() throws ExecutionException, InterruptedException {
        Customer customer = createCustomer();

        customerRepository.create(customer);

        customer.changeName("Samay Pessoa");
        customerRepository.update(customer);
        CompletableFuture<Customer> foundCustomer = customerRepository.find(customer.getId());

        assertNotNull(foundCustomer);
        assertEquals(customer.getName(), foundCustomer.get().getName());
    }

    @Test
    public void shouldFindCustomer() throws ExecutionException, InterruptedException {
        Customer customer = createCustomer();
        customerRepository.create(customer);

        CompletableFuture<Customer> foundCustomer = customerRepository.find(customer.getId());

        assertNotNull(foundCustomer);
        assertEquals(customer.getName(), foundCustomer.get().getName());
    }

    @Test
    public void shouldFindAllCustomers() throws ExecutionException, InterruptedException {
        Customer customer = createCustomer();
        customerRepository.create(customer);

        Customer customer2 = createCustomer();
        customerRepository.create(customer2);

        CompletableFuture<Customer[]> customers = customerRepository.findAll();

        assertEquals(2, customers.get().length);
    }

    public Customer createCustomer() {
        Customer customer = new Customer(UUID.randomUUID().toString(), "Samn");
        Address address = new Address("Rua 2", 10, "64851-452", "Fortal");
        customer.changeAddress(address);
        customer.activate();

        return customer;
    }
}
