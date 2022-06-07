package com.example.sicredidemo.domain.models;

import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Dto para representar um voto com a escolha do voto, a pauta e o associado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voto {
    private ValorVotoEnum voto;
    private UUID pautaId;
    private UUID associadoId;
}
