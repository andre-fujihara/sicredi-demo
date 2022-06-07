package com.example.sicredidemo.domain.scheduler;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Classe para representar um trigger de configuração para agendamento da próxima execução da rotina {@link FechamentoSessaoRoutine}
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FechamentoSessaoTrigger implements Trigger {

    @Value("${fechamentosessao.executioninterval}")
    private Integer INTERVALO_NOVA_TENTATIVA;

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        return DateUtils.addMilliseconds(new Date(), INTERVALO_NOVA_TENTATIVA);
    }
}