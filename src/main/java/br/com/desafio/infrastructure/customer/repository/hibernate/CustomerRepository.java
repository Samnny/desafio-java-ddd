package br.com.desafio.infrastructure.customer.repository.hibernate;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;


import java.util.List;
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        CustomerModel customerModel = entityManager.find(CustomerModel.class, entity.getId());
        if (customerModel == null) {
            throw new RuntimeException("Customer not found");
        }

        customerModel.setName(entity.getName());
        customerModel.setStreet(entity.getAddress().getStreet());
        customerModel.setNumber(entity.getAddress().getNumber());
        customerModel.setZipcode(entity.getAddress().getZip());
        customerModel.setCity(entity.getAddress().getCity());
        customerModel.setActive(entity.isActive());
        customerModel.setRewardPoints(entity.getRewardPoints());

        entityManager.merge(customerModel);
        entityManager.getTransaction().commit();
        entityManager.close();


        return CompletableFuture.completedFuture(null);
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<CustomerModel> customerModels = entityManager.createQuery("SELECT c FROM CustomerModel c", CustomerModel.class).getResultList();
        entityManager.close();

        Customer[] customers = customerModels.stream().map(customerModel -> {
            Customer customer = new Customer(customerModel.getId(), customerModel.getName());
            Address address = new Address(customerModel.getStreet(), customerModel.getNumber(),
                    customerModel.getZipcode(), customerModel.getCity());
            customer.setAddress(address);
            customer.addRewardPoints(customerModel.getRewardPoints());
            customer.setActive(customerModel.getActive());
            return customer;
        }).toArray(Customer[]::new);

        return CompletableFuture.completedFuture(customers);
    }

    public void close() {
        entityManagerFactory.close();
    }
}
