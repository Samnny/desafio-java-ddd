package br.com.desafio.infrastructure.api.routes.customer.controller;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.usecase.customer.create.CreateCustomerUsecase;
import br.com.desafio.usecase.customer.create.InputCreateCustomerDto;
import br.com.desafio.usecase.customer.list.InputListCustomerDto;
import br.com.desafio.usecase.customer.list.ListCustomerUsecase;
import br.com.desafio.usecase.customer.list.OutputListCustomerDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CreateCustomerUsecase createCustomerUsecase;

    @Autowired
    private ListCustomerUsecase listCustomerUsecase;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var input = new InputCreateCustomerDto(customer.getName(), customer.getAddress());

        createCustomerUsecase.execute(input);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OutputListCustomerDto>> getAllCustomers() {
        var customerArray = listCustomerUsecase.execute(new InputListCustomerDto()).join();
        List<OutputListCustomerDto> customers = Arrays.asList(customerArray);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
