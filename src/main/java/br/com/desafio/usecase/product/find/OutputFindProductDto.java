package br.com.desafio.usecase.product.find;

public record OutputFindProductDto(
        String id,
        String name,
        Double price
) {
}
