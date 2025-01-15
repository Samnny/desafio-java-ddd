package br.com.desafio.usecase.customer.create;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.valueobject.Address;
import br.com.desafio.infrastructure.customer.repository.hibernate.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CreateCustomerUsecaseTest {

    private InputCreateCustomerDto input = new InputCreateCustomerDto("John", new Address("rua 2", 10, "60571789", "Fortal"));

    @Test
    public void shouldCreateCustomer() throws ExecutionException, InterruptedException {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        CreateCustomerUsecase createCustomerUsecase = new CreateCustomerUsecase(customerRepository);
        CompletableFuture<OutputCreateCustomerDto> output = createCustomerUsecase.execute(input);

        assertNotNull(output);
        assertEquals("John", output.get().name());
        assertEquals("rua 2", output.get().address().getStreet());
        assertEquals(10, output.get().address().getNumber());
        assertEquals("60571789", output.get().address().getZip());
        assertEquals("Fortal", output.get().address().getCity());

        verify(customerRepository, times(1)).create(Mockito.any(Customer.class));
    }
}
