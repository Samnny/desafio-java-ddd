package br.com.desafio.usecase.customer.update;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.factory.CustomerFactory;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateCustomerUsecaseTest {

    @Test
    void shouldUpdateCustomer() throws Exception {

        Customer customer = CustomerFactory.createWithAddress("Samn",
                new Address("Rua 2", 10, "12345-789", "Fortal")
        );

        InputUpdateCustomerDto input = new InputUpdateCustomerDto(
                customer.getId(),
                "Samn updated",
                new Address("Rua 2 updated", 102, "12345-789 updated", "Fortal updated")
        );

        CustomerRepositoryInterface customerRepository = Mockito.mock(CustomerRepositoryInterface.class);

        when(customerRepository.find(customer.getId()))
                .thenReturn(CompletableFuture.completedFuture(customer));

        doAnswer(invocation -> CompletableFuture.completedFuture(null))
                .when(customerRepository).update(any(Customer.class));

        UpdateCustomerUsecase updateCustomerUseCase = new UpdateCustomerUsecase(customerRepository);

        CompletableFuture<OutputUpdateCustomerDto> outputFuture = updateCustomerUseCase.execute(input);
        OutputUpdateCustomerDto output = outputFuture.join();

        assertEquals(input.id(), output.id());
        assertEquals(input.name(), output.name());
        assertEquals(input.address().getStreet(), output.address().getStreet());
        assertEquals(input.address().getNumber(), output.address().getNumber());
        assertEquals(input.address().getZip(), output.address().getZip());
        assertEquals(input.address().getCity(), output.address().getCity());

        verify(customerRepository, times(1)).find(customer.getId());
        verify(customerRepository, times(1)).update(customer);
    }

}
