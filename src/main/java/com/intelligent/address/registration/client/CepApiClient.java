package com.intelligent.address.registration.client;


import com.intelligent.address.registration.dto.CepResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "cepApiClient", url = "${cep.api.url}")
public interface CepApiClient {

    @GetMapping("/ceps")
    List<CepResponseDTO> fetchAllCeps();
}
