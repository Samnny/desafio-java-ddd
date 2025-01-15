package br.com.desafio.usecase.customer.create;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.factory.CustomerFactory;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CreateCustomerUsecase {

    private CustomerRepositoryInterface customerRepository;

    public CreateCustomerUsecase(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CompletableFuture<OutputCreateCustomerDto> execute(InputCreateCustomerDto input) {
        return CompletableFuture.supplyAsync(() -> {
            Customer customer = CustomerFactory.createWithAddress(input.name(), new Address(
                    input.address().getStreet(), input.address().getNumber(), input.address().getZip(), input.address().getCity()
            ));
            customer.setActive(true);
            this.customerRepository.create(customer);

            return new OutputCreateCustomerDto(customer.getId(), customer.getName(), customer.getAddress());
        });
    }
}
