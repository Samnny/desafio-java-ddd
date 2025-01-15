package br.com.desafio.usecase.product.list;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ListProductUsecase {

    private final ProductRepositoryInterface productRepository;

    public ListProductUsecase(ProductRepositoryInterface productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<OutputListProductDto> execute(InputListProductDto input) {
        return productRepository.findAll()
                .thenApply(Arrays::asList)
                .thenApply(ListProductUsecase.OutputMapper::toOutput);
    }

    static class OutputMapper {

        static OutputListProductDto toOutput(List<Product> products) {
            List<OutputListProductDto.Product> productsDto = products.stream()
                    .map(product -> new OutputListProductDto.Product(
                            product.getId(),
                            product.getName(),
                            product.getPrice()
                    ))
                    .toList();
            return new OutputListProductDto(productsDto);
        }
    }
}
