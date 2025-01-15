package br.com.desafio.usecase.customer.find;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FindCustomerUsecase {

    private CustomerRepositoryInterface customerRepository;

    public FindCustomerUsecase(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CompletableFuture<OutputFindCustomerDto> execute(InputFindCustomerDto input){
        return customerRepository.find(input.id())
                .thenApply(customer -> new OutputFindCustomerDto(
                        customer.getId(),
                        customer.getName(),
                        new OutputFindCustomerDto.Address(
                                customer.getAddress().getStreet(),
                                customer.getAddress().getNumber(),
                                customer.getAddress().getZip(),
                                customer.getAddress().getCity()
                        )
                )).exceptionally(ex -> {
                    throw new RuntimeException("Customer not found");
                });
    }
}
