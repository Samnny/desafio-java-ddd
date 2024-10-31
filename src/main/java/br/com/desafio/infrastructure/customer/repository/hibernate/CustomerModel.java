package br.com.desafio.infrastructure.customer.repository.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class CustomerModel {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Integer rewardPoints;

    public CustomerModel() {}

    public CustomerModel(String id, String name, String street, Integer number, String zipcode, String city, Boolean active, Integer rewardPoints) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.number = number;
        this.zipcode = zipcode;
        this.city = city;
        this.active = active;
        this.rewardPoints = rewardPoints;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }
}
