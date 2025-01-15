package br.com.desafio.usecase.product.find;

import br.com.desafio.domain.product.repository.ProductRepositoryInterface;

import java.util.concurrent.CompletableFuture;

public class FindProductUsecase {

    private final ProductRepositoryInterface productRepository;

    public FindProductUsecase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<OutputFindProductDto> execute(InputFindProductDto input) {
        return productRepository.find(input.id())
                .thenApply(product -> new OutputFindProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ));
    }
}
