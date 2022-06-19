package com.example.sicredidemo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entidade para representar o {@link com.example.sicredidemo.domain.models.Associado} no banco de dado
 */
@Document("Associado")
@Data
@Builder
public class AssociadoEntity {
    @Id
    private String id;
    private String cpf;
    private boolean valido;
}
