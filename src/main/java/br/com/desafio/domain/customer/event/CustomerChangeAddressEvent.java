package br.com.desafio.domain.customer.event;

import br.com.desafio.domain.shared.event.EventInterface;

import java.util.Date;

public class CustomerChangeAddressEvent implements EventInterface {
    private Date dataTimeOccurred;
    private Object eventData;

    public CustomerChangeAddressEvent(Object eventData) {
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
