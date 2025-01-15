package br.com.desafio.infrastructure.api.routes.product.controller;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.product.entity.Product;
import br.com.desafio.usecase.customer.create.InputCreateCustomerDto;
import br.com.desafio.usecase.customer.list.InputListCustomerDto;
import br.com.desafio.usecase.customer.list.OutputListCustomerDto;
import br.com.desafio.usecase.product.create.CreateProductUsecase;
import br.com.desafio.usecase.product.create.InputCreateProductDto;
import br.com.desafio.usecase.product.create.OutputCreateProductDto;
import br.com.desafio.usecase.product.list.InputListProductDto;
import br.com.desafio.usecase.product.list.ListProductUsecase;
import br.com.desafio.usecase.product.list.OutputListProductDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CreateProductUsecase createProductUseCase;

    @Autowired
    private ListProductUsecase listProductUseCase;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody InputCreateProductDto product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var input = new InputCreateProductDto(product.name(), product.price());

        createProductUseCase.execute(input);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OutputListProductDto>> getAllProducts() {
        var productArray = listProductUseCase.execute(new InputListProductDto()).join();
        List<OutputListProductDto> products = Arrays.asList(productArray);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
