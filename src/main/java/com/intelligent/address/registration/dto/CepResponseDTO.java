package com.intelligent.address.registration.dto;

import lombok.Builder;

@Builder
public record CepResponseDTO( String cep,
         String street,
         String complement,
         String District,
         String city,
         String state) {
}
