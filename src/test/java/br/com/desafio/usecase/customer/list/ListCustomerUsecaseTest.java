package br.com.desafio.usecase.customer.list;

import br.com.desafio.domain.customer.entity.Customer;
import br.com.desafio.domain.customer.repository.CustomerRepositoryInterface;
import br.com.desafio.domain.customer.valueobject.Address;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ListCustomerUsecaseTest {

    @Test
    public void shouldListCustomers() throws Exception {
        Address address1 = new Address("Rua 2", 10, "45698-785", "Fortal");
        Customer customer1 = new Customer("1", "Samn");
        customer1.setAddress(address1);

        Address address2 = new Address("Rua 3", 20, "45798-785", "Fortal");
        Customer customer2 = new Customer("2", "Pedro");
        customer2.setAddress(address2);

        CustomerRepositoryInterface customerRepository = Mockito.mock(CustomerRepositoryInterface.class);

        when(customerRepository.findAll())
                .thenReturn(CompletableFuture.completedFuture(new Customer[]{customer1, customer2}));

        ListCustomerUsecase usecase = new ListCustomerUsecase(customerRepository);

        CompletableFuture<OutputListCustomerDto> futureOutput = usecase.execute(new InputListCustomerDto());
        OutputListCustomerDto output = futureOutput.get();

        assertNotNull(output);
        assertEquals(2, output.customers().size());

        OutputListCustomerDto.Customer outputCustomer1 = output.customers().get(0);
        assertEquals("1", outputCustomer1.id());
        assertEquals("Samn", outputCustomer1.name());
        assertEquals("Rua 2", outputCustomer1.address().street());

        OutputListCustomerDto.Customer outputCustomer2 = output.customers().get(1);
        assertEquals("2", outputCustomer2.id());
        assertEquals("Pedro", outputCustomer2.name());
        assertEquals("Rua 3", outputCustomer2.address().street());
    }
}
