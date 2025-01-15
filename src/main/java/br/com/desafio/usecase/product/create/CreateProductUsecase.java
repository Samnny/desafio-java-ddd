package br.com.desafio.usecase.product.create;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.factory.ProductFactory;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CreateProductUsecase {

    private final ProductRepositoryInterface productRepository;

    public CreateProductUsecase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<OutputCreateProductDto> execute(InputCreateProductDto input) {
        return CompletableFuture.supplyAsync(() -> {
            Product product = ProductFactory.create(input.name(), input.price());

            productRepository.create(product);

            return new OutputCreateProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice()
            );
        });
    }
}
