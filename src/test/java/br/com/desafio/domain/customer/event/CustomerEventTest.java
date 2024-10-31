package br.com.desafio.domain.customer.event;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.event.handler.SendConsoleLog1IsCreatedHandler;
import br.com.desafio.domain.customer.event.handler.SendConsoleLog2IsCreatedHandler;
import br.com.desafio.domain.customer.event.handler.SendConsoleLogIsChangeAddressHandler;
import br.com.desafio.domain.customer.valueobject.Address;
import br.com.desafio.domain.shared.event.EventDispatcher;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class CustomerEventTest {

    @Test
    public void shouldNotifyAllEventCreatedCustomer() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler1 = spy(new SendConsoleLog1IsCreatedHandler());
        var eventHandler2 = spy(new SendConsoleLog2IsCreatedHandler());

        eventDispatcher.register("CustomerCreatedEvent", eventHandler1);
        eventDispatcher.register("CustomerCreatedEvent", eventHandler2);

        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(
                new Customer(UUID.randomUUID().toString(), "Samns")
        );

        eventDispatcher.notify(customerCreatedEvent);
        verify(eventHandler1).handle(customerCreatedEvent);
        verify(eventHandler2).handle(customerCreatedEvent);
    }

    @Test
    public void shouldNotifyAllEventCustomerChangedAddress() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler1 = spy(new SendConsoleLogIsChangeAddressHandler());

        eventDispatcher.register("CustomerChangeAddressEvent", eventHandler1);

        Customer customer = new Customer(UUID.randomUUID().toString(), "Samns");
        Address address = new Address("Rua 2", 10, "8555454", "Jeri");
        customer.setAddress(address);
        customer.activate();

        CustomerChangeAddressEvent customerChangedAddressEvent = new CustomerChangeAddressEvent(
                customer
        );

        eventDispatcher.notify(customerChangedAddressEvent);

        verify(eventHandler1).handle(customerChangedAddressEvent);
    }
}
