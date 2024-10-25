package br.com.desafio.domain.customer.valueobject;

public class Address {

    private String street;
    private Integer number;
    private String zip;
    private String city;

    public Address(String street, Integer number, String zip, String city) {
        this.street = street;
        this.number = number;
        this.zip = zip;
        this.city = city;
        this.validate();
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    private void validate() {
        if (this.street == null || this.street.isEmpty()) {
            throw new Error("Street is required");
        }
        if (this.number == null || this.number < 0) {
            throw new Error("Number is required");
        }
        if (this.zip == null || this.zip.isEmpty()) {
            throw new Error("Zip is required");
        }
        if (this.city == null || this.city.isEmpty()) {
            throw new Error("City is required");
        }
    }
}
