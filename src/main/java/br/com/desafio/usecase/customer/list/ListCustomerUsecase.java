package br.com.desafio.usecase.customer.list;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ListCustomerUsecase {

    private final CustomerRepositoryInterface customerRepository;

    public ListCustomerUsecase(CustomerRepositoryInterface customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CompletableFuture<OutputListCustomerDto> execute(InputListCustomerDto input) {
        return customerRepository.findAll()
                .thenApply(Arrays::asList)
                .thenApply(OutputMapper::toOutput);
    }

    static class OutputMapper {

        static OutputListCustomerDto toOutput(List<Customer> customers) {
            List<OutputListCustomerDto.Customer> customerDtos = customers.stream()
                    .map(customer -> new OutputListCustomerDto.Customer(
                            customer.getId(),
                            customer.getName(),
                            new OutputListCustomerDto.Address(
                                    customer.getAddress().getStreet(),
                                    customer.getAddress().getNumber(),
                                    customer.getAddress().getZip(),
                                    customer.getAddress().getCity()
                            )
                    ))
                    .toList();
            return new OutputListCustomerDto(customerDtos);
        }
    }
}
