package br.com.desafio.domain.shared.event;

public interface EventDispatcherInterface {
    void notify(EventInterface event);
    void register(String eventName, EventHandlerInterface eventHandler);
    void unregister(String eventName, EventHandlerInterface eventHandler);
    void unregisterAll();
}
