package com.example.sicredidemo.config;

import com.example.sicredidemo.scheduler.FechamentoSessaoRoutine;
import com.example.sicredidemo.scheduler.FechamentoSessaoTrigger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Classe para criar um agendador que finalizar as sessões vencidas e executar a rotina de fechamento de sessão.
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchedulingConfiguration implements SchedulingConfigurer {

    private final FechamentoSessaoRoutine fechamentoSessaoRoutine;
    private final FechamentoSessaoTrigger fechamentoSessaoTrigger;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(fechamentoSessaoRoutine, fechamentoSessaoTrigger);
    }
}
