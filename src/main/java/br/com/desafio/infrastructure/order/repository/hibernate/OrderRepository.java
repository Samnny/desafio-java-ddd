package br.com.desafio.infrastructure.order.repository.hibernate;

import br.com.desafio.domain.checkout.entity.Order;
import br.com.desafio.domain.checkout.entity.OrderItem;
import br.com.desafio.domain.checkout.repository.OrderRepositoryInterface;
import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerModel;
import br.com.desafio.infrastructure.product.repository.hibernate.ProductModel;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class OrderRepository implements OrderRepositoryInterface {

    private final EntityManagerFactory entityManagerFactory;

    public OrderRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public CompletableFuture<Void> create(Order entity) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        CustomerModel customerModel = entityManager.find(CustomerModel.class, entity.getCustomerId());
        List<ProductModel> productsModel = entity.getItems().stream().map(item -> entityManager.find(ProductModel.class, item.getProductId())).toList();

        List<OrderItemModel> orderItems = entity.getItems().stream().map(item -> new OrderItemModel(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQuantity()
        )).toList();

        OrderModel orderModel = new OrderModel(
                entity.getId(),
                customerModel,
                orderItems,
                entity.total()
        );

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItemModel orderItem = orderItems.get(i);
            ProductModel product = productsModel.get(i);
            orderItem.setProduct(product);
            orderItem.setOrder(orderModel);
        }

        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item.");
        }

        entityManager.persist(orderModel);
        entityManager.getTransaction().commit();
        entityManager.close();

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> update(Order entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        OrderModel orderModel = entityManager.find(OrderModel.class, entity.getId());
        if (orderModel == null) {
            throw new RuntimeException("Order not found");
        }

        CustomerModel customerModel = entityManager.find(CustomerModel.class, entity.getCustomerId());
        orderModel.setCustomer(customerModel);

        List<ProductModel> productsModel = entity.getItems().stream().map(item -> entityManager.find(ProductModel.class, item.getProductId())).toList();

        List<OrderItemModel> orderItems = entity.getItems().stream().map(item -> new OrderItemModel(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getQuantity()
        )).toList();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItemModel orderItem = orderItems.get(i);
            ProductModel product = productsModel.get(i);
            orderItem.setProduct(product);
            orderItem.setOrder(orderModel);
        }

        orderModel.setOrderItems(orderItems);
        orderModel.setTotal(entity.total());

        entityManager.merge(orderModel);
        entityManager.getTransaction().commit();

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();


        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Order> find(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrderModel entity = entityManager.find(OrderModel.class, id);
        entityManager.close();

        if (entity == null) {
            throw new RuntimeException("Order not found");
        }

        List<OrderItem> orderItems = entity.getOrderItems().stream().map(item -> new OrderItem(
                item.getId(),
                item.getProduct().getId(),
                item.getName(),
                item.getPrice(),
                item.getQuantity()
        )).toList();

        Order order = new Order(entity.getId(), entity.getCustomer().getId(), orderItems);

        return CompletableFuture.completedFuture(order);
    }

    @Override
    public CompletableFuture<Order[]> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<OrderModel> orderModels = entityManager.createQuery("SELECT o FROM OrderModel o", OrderModel.class).getResultList();
        entityManager.close();

        Order[] orders = orderModels.stream().map(orderModel -> {
            List<OrderItem> orderItems = orderModel.getOrderItems().stream().map(item -> new OrderItem(
                    item.getId(),
                    item.getProduct().getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getQuantity()
            )).toList();
            Order order = new Order(orderModel.getId(), orderModel.getCustomer().getId(), orderItems);
            return order;
        }).toArray(Order[]::new);

        return CompletableFuture.completedFuture(orders);
    }

    public void close() {
        entityManagerFactory.close();
    }
}
