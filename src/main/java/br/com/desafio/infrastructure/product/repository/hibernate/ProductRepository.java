package br.com.desafio.infrastructure.product.repository.hibernate;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class ProductRepository implements ProductRepositoryInterface {

    private final EntityManagerFactory entityManagerFactory;

    public ProductRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public CompletableFuture<Void> create(Product entity) {

        ProductModel productModel = new ProductModel(
                entity.getId(), entity.getName(), entity.getPrice()
        );

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(productModel);
        entityManager.getTransaction().commit();
        entityManager.close();

        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> update(Product entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        ProductModel productModel = entityManager.find(ProductModel.class, entity.getId());
        if (productModel == null) {
            throw new RuntimeException("Product not found");
        }

        productModel.setName(entity.getName());
        productModel.setPrice(entity.getPrice());

        entityManager.merge(productModel);
        entityManager.getTransaction().commit();

        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();


        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Product> find(String id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ProductModel entity = entityManager.find(ProductModel.class, id);
        entityManager.close();

        if (entity == null) {
            throw new RuntimeException("Product not found");
        }

        Product product = new Product(entity.getId(), entity.getName(), entity.getPrice());

        return CompletableFuture.completedFuture(product);
    }

    @Override
    public CompletableFuture<Product[]> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ProductModel> productModels = entityManager.createQuery("SELECT p FROM ProductModel p", ProductModel.class).getResultList();
        entityManager.close();

        Product[] products = productModels.stream().map(productModel -> {
            Product product = new Product(productModel.getId(), productModel.getName(), productModel.getPrice());
            return product;
        }).toArray(Product[]::new);

        return CompletableFuture.completedFuture(products);
    }

    public void close() {
        entityManagerFactory.close();
    }
}
