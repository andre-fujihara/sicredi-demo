package com.example.sicredidemo.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * Dto para representar uma pauta no sistema e virtualmente tamém uma sessão
 * Uma sessão é uma pauta que foi iniciada e tem suas datas de inicio e fim
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pauta {
    private UUID id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private Boolean sessaoFinalizada;
}
