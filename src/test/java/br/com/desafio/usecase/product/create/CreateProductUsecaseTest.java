package br.com.desafio.usecase.product.create;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateProductUsecaseTest {

    @Test
    void shouldCreateProduct() throws Exception {
        InputCreateProductDto input = new InputCreateProductDto("Product", 1.0);

        ProductRepositoryInterface productRepository = Mockito.mock(ProductRepositoryInterface.class);
        when(productRepository.create(any(Product.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        CreateProductUsecase createProductUseCase = new CreateProductUsecase(productRepository);

        CompletableFuture<OutputCreateProductDto> outputFuture = createProductUseCase.execute(input);
        OutputCreateProductDto output = outputFuture.join();


        assertNotNull(output.id());
        assertEquals(input.name(), output.name());
        assertEquals(input.name(), output.name());

        verify(productRepository, times(1)).create(any(Product.class));
    }
}
