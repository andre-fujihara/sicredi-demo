package com.example.sicredidemo.domain.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Classe que representa os erros encontrados e tratados pelo sistema
 */
@Data
public class SicrediErroException extends RuntimeException {
    private HttpStatus status;

    /**
     * Construtor do erro com o código do status do erro para retorno à API
     * @param message
     * @param status
     */
    public SicrediErroException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
