package br.com.desafio.domain.customer.factory;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerFactoryTest {

    @Test
    public void shouldCreateCustomer() {

        Customer customer = CustomerFactory.create("Samn Pess");

        assertNotNull(customer.getId());
        assertEquals("Samn Pess", customer.getName());
        assertNull(customer.getAddress());
    }

    @Test
    public void shouldCreateCustomerWithAddress() {

        Address address = new Address("Rua 2", 10, "65784-785", "Fortal");
        Customer customer = CustomerFactory.create("Samn Pess");
        customer.setAddress(address);

        assertNotNull(customer.getId());
        assertEquals("Samn Pess", customer.getName());
        assertEquals(address, customer.getAddress());
    }
}
