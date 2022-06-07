package com.example.sicredidemo.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Dto para representar um associado no sistema
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Associado {
    private UUID id;
    private String cpf;
}
