package com.example.sicredidemo.api.dto.response;

import com.example.sicredidemo.domain.models.Associado;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Classe de transporte representando uma response do associado
 */
@Data
@Builder
public class AssociadoResponse {
    private UUID id;
    private String cpf;

    public static AssociadoResponse makeResponse(Associado associado) {
        return AssociadoResponse.builder()
                .id(associado.getId())
                .cpf(associado.getCpf())
                .build();
    }
}
