package com.example.sicredidemo.domain.external;

import com.example.sicredidemo.enums.UsuarioStatusEnum;
import lombok.Data;

/**
 * Classe que encapsula a resposta externa do status de um usuário
 */
@Data
public class UsuarioStatus {
    private UsuarioStatusEnum status;
}
