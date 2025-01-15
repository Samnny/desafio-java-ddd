package br.com.desafio.usecase.product.create;

public record OutputCreateProductDto(
        String id,
        String name,
        Double price
) {
}
