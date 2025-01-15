package br.com.desafio.usecase.product.update;

import br.com.desafio.domain.product.repository.ProductRepositoryInterface;

import java.util.concurrent.CompletableFuture;

public class UpdateProductUsecase {

    private final ProductRepositoryInterface productRepository;

    public UpdateProductUsecase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<OutputUpdateProductDto> execute(InputUpdateProductDto input) {
        return productRepository.find(input.id())
                .thenApplyAsync(product -> {
                    if (product == null) {
                        throw new RuntimeException("Product not found");
                    }

                    product.changeName(input.name());
                    product.changePrice(input.price());

                    return product;
                })
                .thenComposeAsync(product -> productRepository.update(product)
                        .thenApplyAsync(ignored -> new OutputUpdateProductDto(
                                product.getId(),
                                product.getName(),
                                product.getPrice()
                        ))
                );
    }
}
