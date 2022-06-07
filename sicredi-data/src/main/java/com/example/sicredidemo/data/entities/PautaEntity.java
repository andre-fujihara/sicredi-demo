package com.example.sicredidemo.data.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

/**
 * Entidade para representar a {@link com.example.sicredidemo.domain.models.Pauta} no banco de dado
 */
@Document("Pauta")
@Data
@Builder
public class PautaEntity {
    @Id
    private UUID id;
    private String nome;
    @Field("data_inicio")
    private Date dataInicio;
    @Field("data_fim")
    private Date dataFim;
    @Field("sessao_finalizada")
    private Boolean sessaoFinalizada;

}
