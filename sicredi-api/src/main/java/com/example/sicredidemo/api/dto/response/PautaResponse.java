package com.example.sicredidemo.api.dto.response;

import com.example.sicredidemo.domain.models.Pauta;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Classe de transporte representando uma response da pauta
 */
@Data
@Builder
public class PautaResponse {
    private UUID id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private Boolean sessaoFinalizada;

    public static PautaResponse makeResponse(Pauta pauta) {
        return PautaResponse.builder()
                .id(pauta.getId())
                .nome(pauta.getNome())
                .dataInicio(pauta.getDataInicio())
                .dataFim(pauta.getDataFim())
                .sessaoFinalizada(pauta.getSessaoFinalizada())
                .build();
    }
}
