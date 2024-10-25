package br.com.desafio.domain.customer.entity;

import br.com.desafio.domain.customer.valueobject.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    private Customer customer;

    @Test
    public void shouldThrowErrorWhenIdIsEmpty() {

        Error exception = assertThrows(Error.class, () -> {
            new Customer("", "John");
        });
        assertEquals("Id is required", exception.getMessage());
    }

    @Test
    public void shouldThrowErrorWhenNameIsEmpty() {

        Error exception = assertThrows(Error.class, () -> {
            new Customer("123", "");
        });
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    public void shouldChangeName() {
        Customer customer = new Customer("123", "John");
        customer.changeName("Jane");
        assertEquals("Jane", customer.getName());
    }

    @Test
    public void shouldActivateCustomer() {
        customer = new Customer("123", "John");
        Address address = new Address("Rua 2", 10, "78945-452", "São Benedito");
        customer.setAddress(address);
        customer.activate();
        assertEquals(customer.isActive(), true);
    }

    @Test
    public void shouldThrowErrorWhenAddressIsUndefinedWhenActivateCustomer() {
        customer = new Customer("123", "John");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customer.activate();
        });
        assertEquals("Address is mandatory to activate a customer", exception.getMessage());
    }

    @Test
    public void shouldDeactivateCustomer() {
        customer = new Customer("123", "John");
        customer.deactivate();
        assertEquals(customer.isActive(), false);
    }

    @Test
    public void shouldAddRewardPoints() {
        customer = new Customer("123", "John");
        assertEquals(0, customer.getRewardPoints());
        customer.addRewardPoints(10);
        assertEquals(10, customer.getRewardPoints());
        customer.addRewardPoints(20);
        assertEquals(30, customer.getRewardPoints());
    }
}
