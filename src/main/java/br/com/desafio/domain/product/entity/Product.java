package br.com.desafio.domain.product.entity;

public class Product implements ProductInterface {

    private String id;
    private String name;
    private Double price;

    public Product(String id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.validate();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }

    public void validate() {
        if (this.id == null || this.id.isEmpty()) {
            throw new IllegalArgumentException("Id is required");
        }
        if (this.name == null || this.name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (this.price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }

    public void changeName(String name) {
        this.name = name;
        this.validate();
    }

    public void changePrice(Double price) {
        this.price = price;
        this.validate();
    }
}
