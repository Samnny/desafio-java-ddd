package br.com.desafio.domain.shared.event;

public interface EventHandlerInterface<T extends EventInterface> {
    void handle(T event);
}
