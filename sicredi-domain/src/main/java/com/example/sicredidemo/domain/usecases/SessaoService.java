package com.example.sicredidemo.domain.usecases;

import com.example.sicredidemo.domain.exceptions.SicrediErroException;
import com.example.sicredidemo.domain.interfaces.PautaGateway;
import com.example.sicredidemo.domain.interfaces.VotoGateway;
import com.example.sicredidemo.domain.models.Pauta;
import com.example.sicredidemo.domain.models.ResultadoPauta;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações das Sessões
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoService {

    private static final String TOPIC_NAME = "sessao-finalizada";

    private final PautaGateway pautaGateway;
    private final VotoGateway votoGateway;
    private final ObjectMapper mapper;
    private final KafkaTemplate<Integer,String> kafkaTemplate;

    /**
     * Método para abrir uma sessão na pauta passada com seu intervalo de inicio e fim.
     * Se já existe uma sessão ou ocorre qualquer erros de validaçõa o método está tratando o resultado
     * @param pautaId
     * @param inicio
     * @param fim
     * @return
     */
    public Pauta abrirSessao(UUID pautaId, Date inicio, Date fim) {
        Pauta pauta = pautaGateway.findByIdAndDataInicioIsNull(pautaId).orElseThrow(() ->
                new SicrediErroException("Pauta já iniciada para o ID : " + pautaId, NOT_FOUND));
        pauta.setDataInicio(inicio);
        pauta.setDataFim(fim);
        pauta.setSessaoFinalizada(false);
        return pautaGateway.atualizarPauta(pauta);
    }

    /**
     * Método para finalizar as sessões abertas no momento no qual é chamado.
     */
    public void finalizarSessoes() {
        List<Pauta> pautaNaoFinalizada = pautaGateway.findPautaNaoFinalizada(new Date());
        // Finalizo as pautas
        pautaNaoFinalizada.stream().forEach(
                pauta -> {
                    pauta.setSessaoFinalizada(true);
                    pautaGateway.atualizarPauta(pauta);
                }
        );

        // Transformo o resultado no objeto de retorno do método
         pautaNaoFinalizada.stream().map(pauta -> {
            return ResultadoPauta.builder()
                    .pautaId(pauta.getId())
                    .pautaNome(pauta.getNome())
                    .votosNao(votoGateway.contarVotos(pauta.getId(), ValorVotoEnum.NAO))
                    .votosSim(votoGateway.contarVotos(pauta.getId(), ValorVotoEnum.SIM))
                    .build();
        }).forEach(this::sendToQueue);

    }

    /**
     * Método interno para enviar cada resultado de pauta já finalizadas para o serviço de mensageria
     * @param resultadoSessao
     */
    private void sendToQueue(ResultadoPauta resultadoSessao) {
        try {
            kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(resultadoSessao));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Erro ao parsear o resutado da sessão");
        }
    }


}
