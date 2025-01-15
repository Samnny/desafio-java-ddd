package br.com.desafio.usecase.customer.find;

import br.com.desafio.domain.customer.valueobject.Address;

public record OutputFindCustomerDto(
        String id,
        String name,
        Address address
) {
    public record Address(
            String street,
            int number,
            String zip,
            String city
    ) {}
}
