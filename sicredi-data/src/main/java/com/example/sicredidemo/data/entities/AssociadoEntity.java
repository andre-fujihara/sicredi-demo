package com.example.sicredidemo.data.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Entidade para representar o {@link com.example.sicredidemo.domain.models.Associado} no banco de dado
 */
@Document("Associado")
@Data
@Builder
public class AssociadoEntity {
    @Id
    private UUID id;
    private String cpf;
    private boolean valido;
}
