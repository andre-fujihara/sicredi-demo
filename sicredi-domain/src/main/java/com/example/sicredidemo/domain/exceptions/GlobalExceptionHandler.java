package com.example.sicredidemo.domain.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe para tratar qualquer exceção que ocorra durante o fluxo de execução iniciadas a partir dos endpoints mapeados via Controllers
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Método para tratamento de erros genéricos
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<String> defaultErrorHandler(Exception ex) {
        log.error("Erro capturado: {}", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    /**
     * Método para tratamentos do erros conhecidos e lançados pelo sistema
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {SicrediErroException.class})
    public ResponseEntity<String> dataAccessErrorHandler(SicrediErroException ex) {
        log.debug("Exception: ", ex);

        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

}
