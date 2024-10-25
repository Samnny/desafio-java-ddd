package br.com.desafio.domain.shared.repository;

import java.util.concurrent.CompletableFuture;

public interface RepositoryInterface<T> {

    CompletableFuture<Void> create(T entity);
    CompletableFuture<Void> update(T entity);
    CompletableFuture<T> find(String id);
    CompletableFuture<T[]> findAll();

}
