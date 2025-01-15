package br.com.desafio.usecase.product.list;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.domain.product.factory.ProductFactory;
import br.com.desafio.domain.product.repository.ProductRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ListProductUsecaseTest {

    @Test
    void shouldListAllProducts() throws Exception {

        Product product1 = ProductFactory.create("Product", 1.0);
        Product product2 = ProductFactory.create("Product 2", 2.0);

        ProductRepositoryInterface productRepository = Mockito.mock(ProductRepositoryInterface.class);

        when(productRepository.findAll())
                .thenReturn(CompletableFuture.completedFuture(new Product[]{product1, product2}));

        ListProductUsecase listProductUseCase = new ListProductUsecase(productRepository);

        CompletableFuture<OutputListProductDto> outputFuture = listProductUseCase.execute(new InputListProductDto());
        OutputListProductDto output = outputFuture.join();

        assertEquals(2, output.products().size());

        assertEquals(product1.getId(), output.products().get(0).id());  // Verificando o ID do primeiro produto
        assertEquals(product1.getName(), output.products().get(0).name());  // Verificando o nome do primeiro produto
        assertEquals(product1.getPrice(), output.products().get(0).price());  // Verificando o preço do primeiro produto

        // Asserções para o segundo produto
        assertEquals(product2.getId(), output.products().get(1).id());  // Verificando o ID do segundo produto
        assertEquals(product2.getName(), output.products().get(1).name());  // Verificando o nome do segundo produto
        assertEquals(product2.getPrice(), output.products().get(1).price());  // Verificando o preço do segundo produto
    }
}
