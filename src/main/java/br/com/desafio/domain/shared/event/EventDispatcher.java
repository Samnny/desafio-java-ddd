package br.com.desafio.domain.shared.event;

import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher implements EventDispatcherInterface {

    private Map<String, List<EventHandlerInterface<EventInterface>>> eventHandlers = new HashMap<>();

    public Map<String, List<EventHandlerInterface<EventInterface>>> getEventHandlers() {
        return eventHandlers;
    }
    
    @Override
    public void notify(EventInterface event) {
        var eventName = event.getClass().getSimpleName();
        if (eventHandlers.containsKey(eventName)) {
            eventHandlers.get(eventName).forEach(eventHandler -> {
                eventHandler.handle(event);
            });
        }
    }

    @Override
    public void register(String eventName, EventHandlerInterface eventHandler) {
        if (!eventHandlers.containsKey(eventName)) {
            eventHandlers.put(eventName, new ArrayList<>());
        }
        eventHandlers.get(eventName).add(eventHandler);
    }

    @Override
    public void unregister(String eventName, EventHandlerInterface eventHandler) {
        if (eventHandlers.containsKey(eventName)) {
            eventHandlers.get(eventName).remove(eventHandler);
        }
    }

    @Override
    public void unregisterAll() {
        eventHandlers.clear();
    }
}
