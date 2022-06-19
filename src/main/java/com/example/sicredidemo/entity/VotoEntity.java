package com.example.sicredidemo.entity;

import com.example.sicredidemo.enums.ValorVotoEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entidade para representar o {@link com.example.sicredidemo.domain.models.Voto} no banco de dado
 */
@Document("Voto")
@Data
@Builder
public class VotoEntity {
    @Id
    private String id;
    @Field("pauta_id")
    private String pautaId;
    @Field("associado_id")
    private String associadoId;
    private ValorVotoEnum voto;
}