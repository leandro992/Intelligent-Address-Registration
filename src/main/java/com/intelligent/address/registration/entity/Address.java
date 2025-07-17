package com.intelligent.address.registration.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String cep;
    private String street;
    private String city;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
