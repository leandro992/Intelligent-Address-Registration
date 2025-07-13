package com.intelligent.address.registration.service;

import com.intelligent.address.registration.dto.AddressDTO;
import com.intelligent.address.registration.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address createAddress(String cep);
    Address updateAddress(Long id, AddressDTO dto);
    void deleteAddress(Long id);
}