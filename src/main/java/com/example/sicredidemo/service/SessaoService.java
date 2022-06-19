package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.exception.SicrediErroException;
import com.example.sicredidemo.repository.PautaRepository;
import com.example.sicredidemo.repository.VotoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações das Sessões
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessaoService {

    private static final Integer SESSAO_DURACAO_DEFAULT = 1;
    private static final String TOPIC_NAME = "sessao-finalizada";

    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final ObjectMapper mapper;
    private final KafkaTemplate<Integer,String> kafkaTemplate;

    /**
     * Método para abrir uma sessão na pauta e sua duração.
     * Se já existe uma sessão ou ocorre qualquer erros de validaçõa o método está tratando o resultado
     * @param pautaId
     * @param duracao
     * @return
     */
    public PautaResponse abrirSessao(String pautaId, Integer duracao) {
        int duracaoValor = duracao == null ? SESSAO_DURACAO_DEFAULT : duracao;

        PautaEntity pautaEntity = pautaRepository.findByIdAndDataInicioIsNull(pautaId).orElseThrow(() ->
                new SicrediErroException("Pauta já iniciada para o ID : " + pautaId, NOT_FOUND));
        pautaEntity.setDataInicio(new Date());
        pautaEntity.setDataFim(DateUtils.addMinutes(pautaEntity.getDataInicio(), duracaoValor));
        pautaEntity.setSessaoFinalizada(false);
        pautaRepository.save(pautaEntity);
        return PautaResponse.makeResponse(pautaEntity);
    }

    /**
     * Método para finalizar as sessões abertas no momento no qual é chamado.
     */
    public void finalizarSessoes() {
        List<PautaEntity> pautaNaoFinalizada = pautaRepository.findByDataFimLessThanEqualAndSessaoFinalizadaIsFalse(new Date());
        // Finalizo as pautas
        pautaNaoFinalizada.stream().forEach(
                pauta -> {
                    pauta.setSessaoFinalizada(true);
                    pautaRepository.save(pauta);
                }
        );

        // Transformo o resultado no objeto de retorno do método
         pautaNaoFinalizada.stream().map(pauta -> {
            return ResultadoPautaResponse.builder()
                    .pautaId(pauta.getId())
                    .pautaNome(pauta.getNome())
                    .votosNao(votoRepository.countByPautaIdAndVoto(pauta.getId(), ValorVotoEnum.NAO))
                    .votosSim(votoRepository.countByPautaIdAndVoto(pauta.getId(), ValorVotoEnum.SIM))
                    .build();
        }).forEach(this::sendToQueue);

    }

    /**
     * Método interno para enviar cada resultado de pauta já finalizadas para o serviço de mensageria
     * @param resultadoSessao
     */
    private void sendToQueue(ResultadoPautaResponse resultadoSessao) {
        try {
            kafkaTemplate.send(TOPIC_NAME, mapper.writeValueAsString(resultadoSessao));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Erro ao parsear o resutado da sessão");
        }
    }


}
