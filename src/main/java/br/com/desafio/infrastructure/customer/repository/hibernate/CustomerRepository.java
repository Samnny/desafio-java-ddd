package br.com.desafio.infrastructure.customer.repository.hibernate;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;


import java.util.concurrent.CompletableFuture;

@Repository
public class CustomerRepository implements CustomerRepositoryInterface {

    private final EntityManagerFactory entityManagerFactory;

    public CustomerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    public CompletableFuture<Void> create(Customer entity) {

        CustomerModel customerModel = new CustomerModel(
                entity.getId(), entity.getName(),
                entity.getAddress().getStreet(), entity.getAddress().getNumber(),
                entity.getAddress().getZip(), entity.getAddress().getCity(),
                entity.isActive(), entity.getRewardPoints()
        );

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(customerModel);
        entityManager.getTransaction().commit();
        entityManager.close();

        return CompletableFuture.completedFuture(null);
    }


    public CompletableFuture<Void> update(Customer entity) {
        return null;
    }


    public CompletableFuture<Customer> find(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CustomerModel entity = entityManager.find(CustomerModel.class, id);
        entityManager.close();

        if (entity == null) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = new Customer(entity.getId(), entity.getName());
        Address address = new Address(entity.getStreet(), entity.getNumber(),
                entity.getZipcode(), entity.getCity());
        customer.setAddress(address);
        customer.addRewardPoints(entity.getRewardPoints());
        customer.setActive(entity.getActive());

        return CompletableFuture.completedFuture(customer);
    }


    public CompletableFuture<Customer[]> findAll() {
        return null;
    }

    public void close() {
        entityManagerFactory.close();
    }
}
