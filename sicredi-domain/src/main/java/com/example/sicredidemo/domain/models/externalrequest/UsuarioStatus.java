package com.example.sicredidemo.domain.models.externalrequest;

import com.example.sicredidemo.domain.models.enums.UsuarioStatusEnum;
import lombok.Data;

/**
 * Classe que encapsula a resposta externa do status de um usuário
 */
@Data
public class UsuarioStatus {
    private UsuarioStatusEnum status;
}
