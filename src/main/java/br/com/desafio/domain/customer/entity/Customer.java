package br.com.desafio.domain.customer.entity;

import br.com.desafio.domain.customer.valueobject.Address;

public class Customer {

    private String id;
    private String name;
    private Address address;
    private Boolean active;
    private Integer rewardPoints = 0;

    public Customer() {}

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.validate();
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean isActive() {
        return active;
    }

    public void validate(){
        if(this.id == null || this.id.isEmpty()){
            throw new Error("Id is required");
        }
        if(this.name == null || this.name.isEmpty()){
            throw new Error("Name is required");
        }
    }

    public void changeName(String name) {
        this.name = name;
        this.validate();
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

    public void activate() {
        if(this.address == null) {
            throw new IllegalArgumentException("Address is mandatory to activate a customer");
        }
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void addRewardPoints(Integer points) {
        this.rewardPoints += points;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
