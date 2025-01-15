package br.com.desafio.usecase.customer.create;

import br.com.desafio.domain.customer.valueobject.Address;

public record OutputCreateCustomerDto(
        String id,
        String name,
        Address address
) {}
