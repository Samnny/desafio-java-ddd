package br.com.desafio.domain.checkout.entity;

public class OrderItem {

    private String id;
    private String productId;
    private String name;
    private Double price;
    private Integer quantity;
    private Double total;

    public OrderItem(String id, String productId, String name, Double price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = this.total();
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double total() {
         return price * quantity;
    }
}
