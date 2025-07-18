package com.intelligent.address.registration;

import com.intelligent.address.registration.client.CepApiClient;
import com.intelligent.address.registration.dto.AddressDTO;
import com.intelligent.address.registration.dto.CepResponseDTO;
import com.intelligent.address.registration.entity.Address;
import com.intelligent.address.registration.exception.AddressNotFoundException;
import com.intelligent.address.registration.repository.AddressRepository;
import com.intelligent.address.registration.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CepApiClient cepApiClient;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressDTO addressDTO;
    private CepResponseDTO cepResponseDTO;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .id(1L)
                .cep("01310000")
                .street("Avenida Paulista")
                .city("São Paulo")
                .state("SP")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        addressDTO = new AddressDTO("01310000", "Avenida Paulista", "São Paulo", "SP");

        cepResponseDTO = new CepResponseDTO("01310000", "Avenida Paulista", "", "", "São Paulo", "SP");
    }

    @Test
    void createAddress_shouldCreateAddress_whenCepExists() {
        List<CepResponseDTO> ceps = List.of(cepResponseDTO);
        when(cepApiClient.fetchAllCeps()).thenReturn(ceps);
        when(addressRepository.save(any())).thenReturn(address);


        Address result = addressService.createAddress("01310000");

        assertNotNull(result);
        assertEquals("01310000", result.getCep());
        verify(cepApiClient).fetchAllCeps();
        verify(addressRepository).save(any());
    }

    @Test
    void createAddress_shouldThrowException_whenCepNotFound() {
        List<CepResponseDTO> ceps = List.of(
                new CepResponseDTO("99999999", "Rua X", "", "", "CidadeX", "SP")
        );
        when(cepApiClient.fetchAllCeps()).thenReturn(ceps);

        assertThrows(AddressNotFoundException.class, () -> addressService.createAddress("01310000"));
        verify(cepApiClient).fetchAllCeps();
        verify(addressRepository, never()).save(any());
    }

    @Test
    void getAddressById_shouldReturnAddress_whenExists() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Address result = addressService.getAddressById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getAddressById_shouldThrowException_whenNotFound() {
        when(addressRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.getAddressById(2L));
    }

    @Test
    void updateAddress_shouldUpdate_whenAddressExists() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressRepository.save(any())).thenReturn(address);

        AddressDTO dto = new AddressDTO("01310000", "Nova Rua", "São Paulo", "SP");

        Address result = addressService.updateAddress(1L, dto);

        assertNotNull(result);
        assertEquals("Nova Rua", result.getStreet());
    }

    @Test
    void updateAddress_shouldThrowException_whenAddressNotFound() {
        when(addressRepository.findById(2L)).thenReturn(Optional.empty());
        AddressDTO dto = new AddressDTO("01310000", "Nova Rua", "São Paulo", "SP");

        assertThrows(RuntimeException.class, () -> addressService.updateAddress(2L, dto));
    }

    @Test
    void deleteAddress_shouldDelete_whenAddressExists() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).deleteById(1L);

        assertDoesNotThrow(() -> addressService.deleteAddress(1L));
        verify(addressRepository).deleteById(1L);
    }

    @Test
    void deleteAddress_shouldThrowException_whenAddressNotFound() {
        when(addressRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(2L));
        verify(addressRepository, never()).deleteById(anyLong());
    }
}
