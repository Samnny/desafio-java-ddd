package br.com.desafio.usecase.product.update;

public record InputUpdateProductDto(
        String id,
        String name,
        Double price
) {
}
