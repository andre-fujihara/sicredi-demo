package com.example.sicredidemo.data.entities;

import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * Entidade para representar o {@link com.example.sicredidemo.domain.models.Voto} no banco de dado
 */
@Document("Voto")
@Data
@Builder
public class VotoEntity {
    @Id
    private UUID id;
    @Field("pauta_id")
    private UUID pautaId;
    @Field("associado_id")
    private UUID associadoId;
    private ValorVotoEnum voto;
}