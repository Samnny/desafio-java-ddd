package br.com.desafio.usecase.customer.update;

import br.com.desafio.domain.customer.valueobject.Address;

public record InputUpdateCustomerDto(
        String id,
        String name,
        Address address
) {
}
