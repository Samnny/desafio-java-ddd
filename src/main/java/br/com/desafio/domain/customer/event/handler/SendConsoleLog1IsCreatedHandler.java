package br.com.desafio.domain.customer.event.handler;

import br.com.desafio.domain.customer.event.CustomerCreatedEvent;
import br.com.desafio.domain.shared.event.EventHandlerInterface;

public class SendConsoleLog1IsCreatedHandler implements EventHandlerInterface<CustomerCreatedEvent> {

    @Override
    public void handle(CustomerCreatedEvent event) {

    }
}
