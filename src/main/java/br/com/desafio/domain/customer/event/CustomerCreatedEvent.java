package br.com.desafio.domain.customer.event;

import br.com.desafio.domain.shared.event.EventInterface;

import java.util.Date;

public class CustomerCreatedEvent implements EventInterface {
    private Date dataTimeOccurred;
    private Object eventData;

    public CustomerCreatedEvent(Object eventData) {
        this.eventData = eventData;
        this.dataTimeOccurred = new Date();
    }

    @Override
    public Date getDateTimeOccurred() {
        return dataTimeOccurred;
    }

    @Override
    public Object getEventData() {
        return eventData;
    }
}
