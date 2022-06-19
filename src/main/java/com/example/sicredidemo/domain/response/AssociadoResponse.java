package com.example.sicredidemo.domain.response;

import com.example.sicredidemo.entity.AssociadoEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Classe de transporte representando uma response do associado
 */
@Data
@Builder
public class AssociadoResponse {
    private String id;
    private String cpf;

    public static AssociadoResponse makeResponse(AssociadoEntity associado) {
        return AssociadoResponse.builder()
                .id(associado.getId())
                .cpf(associado.getCpf())
                .build();
    }

}
