package com.example.sicredidemo.domain.response;

import com.example.sicredidemo.entity.PautaEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Classe de transporte representando uma response da pauta
 */
@Data
@Builder
public class PautaResponse {
    private String id;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private Boolean sessaoFinalizada;

    public static PautaResponse makeResponse(PautaEntity pauta) {
        return PautaResponse.builder()
                .id(pauta.getId())
                .nome(pauta.getNome())
                .dataInicio(pauta.getDataInicio())
                .dataFim(pauta.getDataFim())
                .sessaoFinalizada(pauta.getSessaoFinalizada())
                .build();
    }
}
