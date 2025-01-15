package br.com.desafio.usecase.customer.create;

import br.com.desafio.domain.customer.valueobject.Address;

public record InputCreateCustomerDto(
        String name,
        Address address
) {}
