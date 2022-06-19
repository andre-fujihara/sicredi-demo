package com.example.sicredidemo.controller;


import com.example.sicredidemo.controller.SessaoController;
import com.example.sicredidemo.domain.request.AberturaSessaoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.service.SessaoService;
import com.example.sicredidemo.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessaoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private VotoService votoService;

    @InjectMocks
    private SessaoController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String URL_BASE = "/v1/sessao";
    private static final AberturaSessaoRequest request = new AberturaSessaoRequest();
    private static final List<AssociadoResponse> listResponse = List.of(AssociadoResponse.builder().cpf("12345678901").build());

    @Test
    public void GIVEN_request_is_ok_WHEN_criarSessao_DO_associado_criado() throws Exception {
        String pautaId = "pauta_id";
        when(sessaoService.abrirSessao(anyString(), any(Integer.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/pauta/" + pautaId + "/criarsessao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
        verify(sessaoService).abrirSessao(pautaId, null);
    }

    @Test
    public void GIVEN_request_is_empty_WHEN_criarSessao_DO_client_error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void GIVEN_request_is_ok_WHEN_votarPauta_DO_voto_criado() throws Exception {
        String pautaId = "pauta_id";
        String associadoId = "associado_id";
        ValorVotoEnum votoEnum = ValorVotoEnum.NAO;

        doNothing().when(votoService).votarPauta(anyString(), anyString(), any(ValorVotoEnum.class));
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/pauta/" + pautaId + "/associado/" + associadoId + "/votar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(votoEnum))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
        verify(votoService).votarPauta(pautaId, associadoId, votoEnum);
    }

    @Test
    public void GIVEN_request_is_empty_WHEN_votarPauta_DO_client_error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void GIVEN_nothing_WHEN_contarVotosPauta_DO_ResultadoPautaResponse() throws Exception {
        String pautaId = "pauta_id";
        ResultadoPautaResponse resultadoPautaResponse = ResultadoPautaResponse.builder().build();
        when(votoService.contarVotosDaPauta(anyString())).thenReturn(resultadoPautaResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/pauta/" + pautaId + "/votos")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(resultadoPautaResponse)));
        verify(votoService).contarVotosDaPauta(pautaId);
    }

}