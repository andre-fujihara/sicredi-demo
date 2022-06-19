package com.example.sicredidemo.scheduler;

import com.example.sicredidemo.service.SessaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Método que será executado ciclicamente através de um agendador para finalizar as sessões pendentes.
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FechamentoSessaoRoutine implements Runnable {

    private final SessaoService sessaoService;


    @Override
    public void run() {
        log.info(">>> Início do fechamento sessao");
        sessaoService.finalizarSessoes();
        log.info(">>> Término do fechamento de sessao");
    }

}
