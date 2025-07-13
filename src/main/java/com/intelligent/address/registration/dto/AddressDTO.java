package com.intelligent.address.registration.dto;

import lombok.Builder;

@Builder
public record AddressDTO(String cep,
                         String street,
                         String city,
                         String state) {
}