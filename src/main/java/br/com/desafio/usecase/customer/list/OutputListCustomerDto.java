package br.com.desafio.usecase.customer.list;

import java.util.List;

public record OutputListCustomerDto(
        List<Customer> customers
) {
    public record Customer(String id, String name, Address address) {}
    public record Address(String street, int number, String zip, String city) {}
}

