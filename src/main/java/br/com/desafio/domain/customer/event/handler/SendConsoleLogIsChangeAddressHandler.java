package br.com.desafio.domain.customer.event.handler;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.event.CustomerChangeAddressEvent;
import br.com.desafio.domain.shared.event.EventHandlerInterface;

public class SendConsoleLogIsChangeAddressHandler implements EventHandlerInterface<CustomerChangeAddressEvent> {

    @Override
    public void handle(CustomerChangeAddressEvent event) {
        Customer customer = (Customer) event.getEventData();
        System.out.println(STR."Endereço do cliente: \{customer.getId()}, \{customer.getName()} alterado para: \{customer.getAddress().getStreet()}");
    }
}
