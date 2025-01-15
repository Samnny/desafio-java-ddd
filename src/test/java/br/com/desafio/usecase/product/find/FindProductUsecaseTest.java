package br.com.desafio.usecase.product.find;

import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FindProductUsecaseTest {

    @Test
    void shouldFindProduct() {

        Product product = new Product("123", "Product", 100.0);
        InputFindProductDto input = new InputFindProductDto("123");

        ProductRepositoryInterface productRepository = Mockito.mock(ProductRepositoryInterface.class);
        when(productRepository.find(input.id())).thenReturn(CompletableFuture.completedFuture(product));

        FindProductUsecase findProductUseCase = new FindProductUsecase(productRepository);

        CompletableFuture<OutputFindProductDto> outputFuture = findProductUseCase.execute(input);
        OutputFindProductDto output = outputFuture.join();

        assertEquals(product.getId(), output.id());
        assertEquals(product.getName(), output.name());
        assertEquals(product.getPrice(), output.price());

        verify(productRepository, times(1)).find(input.id());
    }
}
