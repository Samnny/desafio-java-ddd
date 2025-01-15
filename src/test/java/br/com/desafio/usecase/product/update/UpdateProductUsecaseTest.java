package br.com.desafio.usecase.product.update;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpdateProductUsecaseTest {

    @Mock
    private ProductRepositoryInterface productRepository;

    private UpdateProductUsecase updateProductUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        updateProductUseCase = new UpdateProductUsecase(productRepository);
    }

    @Test
    public void shouldUpdateProduct() {

        Product product = new Product("1", "Product", 1.0);
        InputUpdateProductDto input = new InputUpdateProductDto("1", "Product updated", 2.0);

        when(productRepository.find(input.id())).thenReturn(CompletableFuture.completedFuture(product));
        when(productRepository.update(any(Product.class))).thenReturn(CompletableFuture.completedFuture(null));

        CompletableFuture<OutputUpdateProductDto> outputFuture = updateProductUseCase.execute(input);

        outputFuture.thenAccept(output -> {
            assertEquals(input.id(), output.id());
            assertEquals(input.name(), output.name());
            assertEquals(input.price(), output.price());
        }).join();
    }
}
