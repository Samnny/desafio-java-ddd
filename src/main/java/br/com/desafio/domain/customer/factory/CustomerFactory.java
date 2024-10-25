package br.com.desafio.domain.customer.factory;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;

import java.util.UUID;

public class CustomerFactory {

    public static Customer create(String name) {
        return new Customer(UUID.randomUUID().toString(), name);
    }

    public static Customer createWithAddress(String name, Address address) {
        var customer = new Customer(UUID.randomUUID().toString(), name);
        customer.changeAddress(address);
        return customer;
    }
}
