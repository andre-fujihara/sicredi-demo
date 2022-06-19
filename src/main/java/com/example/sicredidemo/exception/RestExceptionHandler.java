package com.example.sicredidemo.exception;

import com.example.sicredidemo.domain.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Classe para mapear os erros que podem ocorrer durante as operações iniciadas via controllers
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        log.error("Erro não esperado:{}, mensagem:{} ", ex, ex.getMessage());
        return handleExceptionInternal(ex, obterRespostaDoErro(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ErrorDTO obterRespostaDoErro(Throwable ex) {
        return ErrorDTO.builder()
                .internalMessage(ex.getCause() != null ? ex.getCause().toString() : ex.toString())
                .moreInfo(ex.getMessage())
                .dateTime(Date.from(ZonedDateTime.now().toInstant()))
                .build();
    }

}
