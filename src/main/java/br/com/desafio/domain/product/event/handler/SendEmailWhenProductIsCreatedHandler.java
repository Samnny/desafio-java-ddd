package br.com.desafio.domain.product.event.handler;

import br.com.desafio.domain.product.event.ProductCreatedEvent;
import br.com.desafio.domain.shared.event.EventHandlerInterface;

public class SendEmailWhenProductIsCreatedHandler implements EventHandlerInterface<ProductCreatedEvent> {

    @Override
    public void handle(ProductCreatedEvent event) {
        System.out.println("Sending email to.........");
    }
}
