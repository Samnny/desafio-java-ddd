package br.com.desafio.infrastructure.order.repository.hibernate;

import br.com.desafio.domain.checkout.entity.Order;
import br.com.desafio.domain.checkout.entity.OrderItem;
import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerRepository;
import br.com.desafio.infrastructure.product.repository.hibernate.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderRepositoryTest {

    private EntityManagerFactory entityManagerFactory;

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private EntityManager entityManager;


    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("testPU");
        orderRepository = new OrderRepository(entityManagerFactory);
        customerRepository = new CustomerRepository(entityManagerFactory);
        productRepository = new ProductRepository(entityManagerFactory);
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @AfterEach
    public void tearDown() {
        entityManagerFactory.close();
        orderRepository.close();
        customerRepository.close();
        productRepository.close();
        entityManager.close();
    }

    @Test
    public void shouldCreateOrder() {
        Customer customer = new Customer(UUID.randomUUID().toString(), "John Doe");
        Address address = new Address("Rua 2", 1, "54540-872", "Jeri");
        customer.setAddress(address);
        customer.activate();
        customerRepository.create(customer);

        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.10);
        productRepository.create(product);

        OrderItem orderItem = new OrderItem(UUID.randomUUID().toString(), product.getId(), product.getName(), product.getPrice(), 2);
        Order order = new Order("1", customer.getId(), List.of(orderItem));

        orderRepository.create(order);

        OrderModel orderModel = entityManager.find(OrderModel.class, order.getId());

        assertEquals(orderModel.getId(), order.getId());
    }

    @Test
    public void shouldUpdateOrder() {
        Customer customer = new Customer(UUID.randomUUID().toString(), "John Doe");
        Address address = new Address("Rua 2", 1, "54540-872", "Jeri");
        customer.setAddress(address);
        customer.activate();
        customerRepository.create(customer);

        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.10);
        productRepository.create(product);

        OrderItem orderItem = new OrderItem(UUID.randomUUID().toString(), product.getId(), product.getName(), product.getPrice(), 2);
        Order order = new Order("2", customer.getId(), List.of(orderItem));
        orderRepository.create(order);

        Product product1 = new Product(UUID.randomUUID().toString(), "Sabonete 123", 3.10);
        productRepository.create(product1);

        OrderItem orderItem1 = new OrderItem(UUID.randomUUID().toString(), product1.getId(), product1.getName(), product1.getPrice(), 2);
        order.getItems().add(orderItem1);
        orderRepository.update(order);

        OrderModel orderModel = entityManager.find(OrderModel.class, order.getId());

        assertEquals(orderModel.getId(), order.getId());
        assertEquals(orderModel.getTotal(), 10.40);
        assertNotNull(orderModel.getOrderItems());
        assertEquals(orderModel.getOrderItems().get(0).getId(), orderItem.getId());
        assertEquals(orderModel.getOrderItems().get(1).getId(), orderItem1.getId());
    }

    @Test
    public void shouldFindOrder() throws ExecutionException, InterruptedException {
        Customer customer = new Customer(UUID.randomUUID().toString(), "John Doe");
        Address address = new Address("Rua 2", 1, "54540-872", "Jeri");
        customer.setAddress(address);
        customer.activate();
        customerRepository.create(customer);

        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.10);
        productRepository.create(product);

        OrderItem orderItem = new OrderItem(UUID.randomUUID().toString(), product.getId(), product.getName(), product.getPrice(), 2);
        Order order = new Order("3", customer.getId(), List.of(orderItem));
        orderRepository.create(order);

        CompletableFuture<Order> orderModel = orderRepository.find(order.getId());
        assertEquals(orderModel.get().getId(), order.getId());
    }

    @Test
    public void shouldFindAllOrders() throws ExecutionException, InterruptedException {
        Customer customer = new Customer(UUID.randomUUID().toString(), "John Doe");
        Address address = new Address("Rua 2", 1, "54540-872", "Jeri");
        customer.setAddress(address);
        customer.activate();
        customerRepository.create(customer);

        Product product = new Product(UUID.randomUUID().toString(), "Sabonete", 2.10);
        productRepository.create(product);

        OrderItem orderItem = new OrderItem(UUID.randomUUID().toString(), product.getId(), product.getName(), product.getPrice(), 2);
        Order order = new Order("4", customer.getId(), List.of(orderItem));
        orderRepository.create(order);

        CompletableFuture<Order[]> orders = orderRepository.findAll();

        assertEquals(orders.get().length, 1);
        assertEquals(orders.get()[0].getId(), order.getId());
    }
}
