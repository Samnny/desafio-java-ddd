package br.com.desafio.domain.shared.event;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.event.ProductCreatedEvent;
import br.com.desafio.domain.product.event.handler.SendEmailWhenProductIsCreatedHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class EventDispatcherTest {

    @Test
    public void shouldRegisterAnEventHandler() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler = new SendEmailWhenProductIsCreatedHandler();
        eventDispatcher.register("ProductCreatedEvent", eventHandler);

        assertNotNull(eventDispatcher.getEventHandlers().get("ProductCreatedEvent"));
        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").size(), 1);
        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").getFirst(), eventHandler);
    }

    @Test
    public void shouldUnregisterAnEventHandler() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler = new SendEmailWhenProductIsCreatedHandler();
        eventDispatcher.register("ProductCreatedEvent", eventHandler);

        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").getFirst(), eventHandler);

        eventDispatcher.unregister("ProductCreatedEvent", eventHandler);

        assertNotNull(eventDispatcher.getEventHandlers().get("ProductCreatedEvent"));
        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").size(), 0);
    }

    @Test
    public void shouldUnregisterAllEventHandlers() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler = new SendEmailWhenProductIsCreatedHandler();
        eventDispatcher.register("ProductCreatedEvent", eventHandler);

        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").getFirst(), eventHandler);

        eventDispatcher.unregisterAll();

        assertNull(eventDispatcher.getEventHandlers().get("ProductCreatedEvent"));
    }

    @Test
    public void shouldNotifyAllEventHandlers() {
        EventDispatcher eventDispatcher = new EventDispatcher();
        var eventHandler = spy(new SendEmailWhenProductIsCreatedHandler());

        eventDispatcher.register("ProductCreatedEvent", eventHandler);

        assertEquals(eventDispatcher.getEventHandlers().get("ProductCreatedEvent").getFirst(), eventHandler);

        var productCreatedEvent = new ProductCreatedEvent(
                new Product("1", "Product 1", 10.0)
        );

        eventDispatcher.notify(productCreatedEvent);

        verify(eventHandler).handle(productCreatedEvent);
    }
}
