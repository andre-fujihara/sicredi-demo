package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.request.PautaRequest;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.repository.PautaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaService service;
    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void GIVEN_PautaRequest_WHEN_criarPauta_DO_PautaResponse() {
        PautaRequest request = new PautaRequest();
        PautaEntity pautaEntity = PautaEntity.builder()
                .id(UUID.randomUUID().toString())
                .nome("Pauta Nome")
                .build();
        when(pautaRepository.save(any(PautaEntity.class))).thenReturn(pautaEntity);

        PautaResponse pautaResponse = service.criarPauta(request);

        assertEquals(PautaResponse.makeResponse(pautaEntity), pautaResponse);
        verify(pautaRepository).save(any(PautaEntity.class));
    }

    @Test
    public void GIVEN_nothing_WHEN_recuperarTodos_DO_list_pauta_response() {
        List<PautaEntity> listPauta = List.of(PautaEntity.builder().build());
        List<PautaResponse> ListPautaresponse = listPauta.stream().map(s -> {
            return PautaResponse.makeResponse(s);
        }).collect(Collectors.toList());
        when(pautaRepository.findAll()).thenReturn(listPauta);

        List<PautaResponse> pautaResponses = service.recuperarTodos();

        assertEquals(ListPautaresponse, pautaResponses);
        verify(pautaRepository).findAll();
    }
}
