package br.com.desafio.usecase.product.update;

public record OutputUpdateProductDto(
        String id,
        String name,
        Double price
) {
}