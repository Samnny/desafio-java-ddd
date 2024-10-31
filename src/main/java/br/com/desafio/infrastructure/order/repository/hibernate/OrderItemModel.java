package br.com.desafio.infrastructure.order.repository.hibernate;

import br.com.desafio.infrastructure.product.repository.hibernate.ProductModel;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItemModel {

    @Id
    @Column(nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    public OrderItemModel(String id, String name, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItemModel() {

    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public OrderModel getOrder() {
        return order;
    }

    public ProductModel getProduct() {
        return product;
    }

    public String getId() {
        return id;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
