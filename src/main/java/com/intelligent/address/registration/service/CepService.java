package com.intelligent.address.registration.service;

import com.intelligent.address.registration.dto.CepResponseDTO;

public interface CepService {
    CepResponseDTO getCepData(String cep);

}