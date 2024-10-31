package br.com.desafio.domain.shared.event;

import org.hibernate.mapping.Any;

import java.util.Date;

public interface EventInterface {
    Date getDateTimeOccurred();
    Object getEventData();
}
