package br.com.desafio.infrastructure.order.repository.hibernate;

import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerModel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    @Column(nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItemModel> orderItems;

    @Column(nullable = false)
    private Double total;

    public OrderModel(String id, CustomerModel customerModel, List<OrderItemModel> orderItems, Double total) {
        this.id = id;
        this.customer = customerModel;
        this.orderItems = new ArrayList<>(orderItems);
        this.total = total;
    }

    public OrderModel() {}

    public String getId() {
        return id;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public Double getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = new ArrayList<>(orderItems);
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
