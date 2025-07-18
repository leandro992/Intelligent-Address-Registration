package com.intelligent.address.registration.service.impl;

import com.intelligent.address.registration.client.CepApiClient;
import com.intelligent.address.registration.dto.AddressDTO;
import com.intelligent.address.registration.dto.CepResponseDTO;
import com.intelligent.address.registration.entity.Address;
import com.intelligent.address.registration.exception.AddressNotFoundException;
import com.intelligent.address.registration.repository.AddressRepository;
import com.intelligent.address.registration.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final CepApiClient cepApiClient;
    private final AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return getAddressNotFind(id);
    }

    public Address createAddress(String cep) {
        List<CepResponseDTO> ceps = cepApiClient.fetchAllCeps();
        CepResponseDTO dto = ceps.stream()
                .filter(c -> c.cep().equals(cep))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException("CEP não encontrado: " + cep));

        Address address = Address.builder()
                .cep(dto.cep())
                .street(dto.street())
                .city(dto.city())
                .state(dto.state())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO dto) {
        Address addr = getAddressNotFind(id);
        addr.setId(id);
        addr.setCep(dto.cep());
        addr.setState(dto.state());
        addr.setStreet(dto.street());
        addr.setCity(dto.city());
        addr.setUpdatedAt(LocalDateTime.now());
        return addressRepository.save(addr);

    }

    @Override
    public void deleteAddress(Long id) {
        getAddressNotFind(id);
        addressRepository.deleteById(id);
    }

    private Address getAddressNotFind(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado"));
    }


}
