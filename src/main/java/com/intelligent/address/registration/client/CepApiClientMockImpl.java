package com.intelligent.address.registration.client;

import com.intelligent.address.registration.dto.CepResponseDTO;

public class CepApiClientMockImpl {

    public CepResponseDTO fetchCepData(String cep) {
        return new CepResponseDTO(
                cep,
                "Rua Fictícia",
                "Complemento Mock",
                "Bairro Exemplo",
                "Cidade Mock",
                "MK"
        );
    }
}
