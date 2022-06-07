package com.example.sicredidemo.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Dto para representar o resultado de uma sessão com a identificação da pauta e dos votos
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoPauta {

    @JsonProperty("pauta_id")
    public UUID pautaId;
    @JsonProperty("nome_pauta")
    public String pautaNome;
    @JsonProperty("qte_votos_sim")
    public long votosSim;
    @JsonProperty("qte_votos_nao")
    public long votosNao;
}
