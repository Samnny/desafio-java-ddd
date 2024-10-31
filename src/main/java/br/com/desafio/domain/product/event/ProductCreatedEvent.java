package br.com.desafio.domain.product.event;

import br.com.desafio.domain.shared.event.EventInterface;

import java.util.Date;

public class ProductCreatedEvent implements EventInterface {

    private Date dateTimeOccurred;
    private Object eventData;

    public ProductCreatedEvent(Object eventData) {
        this.dateTimeOccurred = new Date();
        this.eventData = eventData;
    }

    @Override
    public Date getDateTimeOccurred() {
        return dateTimeOccurred;
    }

    @Override
    public Object getEventData() {
        return eventData;
    }
}
