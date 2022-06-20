package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.request.AssociadoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.entity.AssociadoEntity;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.exception.SicrediErroException;
import com.example.sicredidemo.repository.AssociadoRepository;
import com.example.sicredidemo.repository.PautaRepository;
import com.example.sicredidemo.repository.VotoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(MockitoJUnitRunner.class)
public class SessaoServiceTest {

    @InjectMocks
    private SessaoService service;
    @Mock
    PautaRepository pautaRepository;
    @Mock
    VotoRepository votoRepository;
    @Mock
    ObjectMapper mapper;
    @Mock
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Test
    public void GIVEN_pauta_id_e_duracao_WHEN_abrirSessao_DO_atualiza_pauta_com_abrir_sessao() {
        String pautaId = "pauta_id";
        Integer duracao = 5;
        PautaEntity pautaEntity = PautaEntity.builder().build();
        when(pautaRepository.findByIdAndDataInicioIsNull(anyString())).thenReturn(Optional.ofNullable(pautaEntity));

        PautaResponse pautaResponse = service.abrirSessao(pautaId, duracao);

        assertNotNull(pautaResponse.getDataInicio());
        assertNotNull(pautaResponse.getDataFim());
        assertFalse(pautaResponse.getSessaoFinalizada());
        verify(pautaRepository).findByIdAndDataInicioIsNull(pautaId);
        verify(pautaRepository).save(any(PautaEntity.class));
    }

    @Test
    public void GIVEN_incorreto_pauta_id_WHEN_abrirSessao_DO_pauta_nao_encontrada() {
        String pautaId = "pauta_id";
        Integer duracao = 5;
        SicrediErroException expectedException = new SicrediErroException("Pauta já iniciada para o ID : " + pautaId, NOT_FOUND);
        when(pautaRepository.findByIdAndDataInicioIsNull(anyString())).thenReturn(Optional.ofNullable(null));

        SicrediErroException sicrediErroException = assertThrows(SicrediErroException.class, () -> service.abrirSessao(pautaId, duracao));

        assertEquals(expectedException, sicrediErroException);
        verify(pautaRepository).findByIdAndDataInicioIsNull(pautaId);
    }

    @Test
    public void GIVEN_nothing_WHEN_finalizarSessoes_DO_finaliza_sessoes_ativas() throws JsonProcessingException {
        String pautaId = "pauta_id";
        PautaEntity pautaNaoFinalizada = PautaEntity.builder().id(pautaId).build();

        String resultadoVotos = "resultadoVotos";

        when(pautaRepository.findByDataFimLessThanEqualAndSessaoFinalizadaIsFalse(any(Date.class))).thenReturn(List.of(pautaNaoFinalizada));
        when(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.SIM)).thenReturn(2l);
        when(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.NAO)).thenReturn(1l);
        when(mapper.writeValueAsString(any(ResultadoPautaResponse.class))).thenReturn(resultadoVotos);

        service.finalizarSessoes();

        pautaNaoFinalizada.setSessaoFinalizada(true);
        verify(pautaRepository).save(pautaNaoFinalizada);
        verify(votoRepository).countByPautaIdAndVoto(pautaId, ValorVotoEnum.SIM);
        verify(votoRepository).countByPautaIdAndVoto(pautaId, ValorVotoEnum.NAO);
        verify(kafkaTemplate).send("sessao-finalizada", resultadoVotos);
    }

}
