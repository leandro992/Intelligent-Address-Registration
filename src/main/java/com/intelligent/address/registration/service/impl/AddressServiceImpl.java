package com.intelligent.address.registration.service.impl;

import com.intelligent.address.registration.client.CepApiClient;
import com.intelligent.address.registration.dto.AddressDTO;
import com.intelligent.address.registration.dto.CepResponseDTO;
import com.intelligent.address.registration.entity.Address;
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
        CepResponseDTO cepResponse = cepApiClient.fetchCepData(cep);
        Address address = Address.builder()
                .createdAt(LocalDateTime.now())
                .cep(cepResponse.cep())
                .state(cepResponse.state())
                .street(cepResponse.street())
                .city(cepResponse.city())
                .build();
        return addressRepository.save(address);
    }


    @Override
    public Address updateAddress(Long id, AddressDTO dto) {
        Address addr = getAddressNotFind(id);
        Address address = Address.builder()
                .updatedAt(LocalDateTime.now())
                .cep(addr.getCep())
                .state(addr.getState())
                .street(addr.getStreet())
                .city(addr.getCity())
                .build();
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        getAddressNotFind(id);
        addressRepository.deleteById(id);
    }

    private Address getAddressNotFind(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }


}
