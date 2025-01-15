package br.com.desafio.usecase.customer.update;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;

import java.util.concurrent.CompletableFuture;

public class UpdateCustomerUsecase {

    private final CustomerRepositoryInterface customerRepository;

    public UpdateCustomerUsecase(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CompletableFuture<OutputUpdateCustomerDto> execute(InputUpdateCustomerDto input) {
        return customerRepository.find(input.id())
                .thenApplyAsync(customer -> {
                    if (customer == null) {
                        throw new RuntimeException("Customer not found");
                    }

                    customer.changeName(input.name());
                    customer.changeAddress(new Address(
                            input.address().getStreet(),
                            input.address().getNumber(),
                            input.address().getZip(),
                            input.address().getCity()
                    ));

                    return customer;
                })
                .thenComposeAsync(customer -> customerRepository.update(customer)
                        .thenApplyAsync(ignored -> new OutputUpdateCustomerDto(
                                customer.getId(),
                                customer.getName(),
                                customer.getAddress()
                        ))
                );
    }
}
