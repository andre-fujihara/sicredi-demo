package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.request.AssociadoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.entity.AssociadoEntity;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.exception.SicrediErroException;
import com.example.sicredidemo.repository.AssociadoRepository;
import com.example.sicredidemo.repository.PautaRepository;
import com.example.sicredidemo.repository.VotoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(MockitoJUnitRunner.class)
public class VotoServiceTest {

    @InjectMocks
    private VotoService service;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private VotoRepository votoRepository;
    @Mock
    private CPFManagerService cpfManagerService;

    @Test
    public void GIVEN_pauta_id_associado_id_voto_WHEN_votarPauta_DO_registra_votos() {
        String pautaId = "pauta_id";
        String associadoId = "asosciado_id";
        PautaEntity pautaEntity = PautaEntity.builder().id(pautaId).build();
        AssociadoEntity associadoEntity = AssociadoEntity.builder().id(associadoId).cpf("cpf").build();

        when(pautaRepository.findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(anyString(), any(Date.class), any(Date.class))).thenReturn(Optional.ofNullable(pautaEntity));
        when(associadoRepository.findById(anyString())).thenReturn(Optional.ofNullable(associadoEntity));
        when(cpfManagerService.isUsuarioPodeVotar(anyString())).thenReturn(true);
        when(votoRepository.findByPautaIdAndAssociadoId(anyString(),anyString())).thenReturn(Optional.ofNullable(null));

        service.votarPauta(pautaId, associadoId, ValorVotoEnum.SIM);

        verify(pautaRepository).findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(anyString(), any(Date.class), any(Date.class));
        verify(associadoRepository).findById(associadoId);
        verify(cpfManagerService).isUsuarioPodeVotar(associadoEntity.getCpf());
        verify(votoRepository).findByPautaIdAndAssociadoId(pautaId, associadoId);
    }

    @Test
    public void GIVEN_pauta_id_nao_existente_WHEN_votarPauta_DO_retorna_pauta_nao_encontrada() {
        String pautaId = "pauta_id";
        String associadoId = "asosciado_id";
        SicrediErroException expectedException = new SicrediErroException("Pauta não encontrada para o ID : " + pautaId, NOT_FOUND);
        when(pautaRepository.findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(anyString(), any(Date.class), any(Date.class))).thenReturn(Optional.ofNullable(null));

        SicrediErroException sicrediErroException = assertThrows(SicrediErroException.class, () -> service.votarPauta(pautaId, associadoId, ValorVotoEnum.SIM));

        assertEquals(expectedException, sicrediErroException);
        verify(pautaRepository).findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(anyString(), any(Date.class), any(Date.class));
    }


    @Test
    public void GIVEN_pauta_id_WHEN_contarVotosDaPauta_DO_retorna_votos() {
        String pautaId = "pauta_id";
        String pautaNome = "pauta_nome";
        PautaEntity pautaEntity = PautaEntity.builder().id(pautaId).nome(pautaNome).build();
        ResultadoPautaResponse expectedResponse = ResultadoPautaResponse.builder()
                .pautaId(pautaId)
                .pautaNome(pautaNome)
                .votosSim(2)
                .votosNao(1)
                .build();
        when(pautaRepository.findById(anyString())).thenReturn(Optional.ofNullable(pautaEntity));
        when(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.SIM)).thenReturn(2l);
        when(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.NAO)).thenReturn(1l);

        ResultadoPautaResponse resultadoPautaResponse = service.contarVotosDaPauta(pautaId);

        assertEquals(expectedResponse, resultadoPautaResponse);
        verify(pautaRepository).findById(pautaId);
        verify(votoRepository).countByPautaIdAndVoto(pautaId, ValorVotoEnum.SIM);
        verify(votoRepository).countByPautaIdAndVoto(pautaId, ValorVotoEnum.NAO);
    }

}
