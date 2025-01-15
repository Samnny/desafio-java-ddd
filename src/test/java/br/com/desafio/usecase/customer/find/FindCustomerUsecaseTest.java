package br.com.desafio.usecase.customer.find;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FindCustomerUsecaseTest {

    @Test
    public void shouldFindCustomer() throws ExecutionException, InterruptedException {
        CustomerRepositoryInterface customerRepository = mock(CustomerRepositoryInterface.class);

        Customer customer = new Customer("123", "Samn");
        Address address = new Address("Rua 2", 10, "12560-789", "Fortal");
        customer.setAddress(address);

        when(customerRepository.find("123")).thenReturn(CompletableFuture.completedFuture(customer));

        FindCustomerUsecase usecase = new FindCustomerUsecase(customerRepository);

        InputFindCustomerDto input = new InputFindCustomerDto("123");

        CompletableFuture<OutputFindCustomerDto> resultFuture = usecase.execute(input);
        OutputFindCustomerDto result = resultFuture.get();

        assertEquals("123", result.id());
        assertEquals("Samn", result.name());
        assertEquals("Rua 2", result.address().street());
        assertEquals(10, result.address().number());
        assertEquals("12560-789", result.address().zip());
        assertEquals("Fortal", result.address().city());

        verify(customerRepository, times(1)).find("123");
    }
}
