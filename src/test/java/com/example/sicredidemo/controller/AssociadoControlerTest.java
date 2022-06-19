package com.example.sicredidemo.controller;


import com.example.sicredidemo.controller.AssociadoController;
import com.example.sicredidemo.domain.request.AssociadoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.service.AssociadoService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociadoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AssociadoService associadoService;

    @InjectMocks
    private AssociadoController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String URL_BASE = "/v1/associado";
    private static final AssociadoRequest request = new AssociadoRequest();
    private static final List<AssociadoResponse> listResponse = List.of(AssociadoResponse.builder().cpf("12345678901").build());

    @Test
    public void GIVEN_request_is_ok_WHEN_criarAssociado_DO_associado_criado() throws Exception {
        when(associadoService.criarAssociado(any(AssociadoRequest.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
        verify(associadoService).criarAssociado(request);
    }

    @Test
    public void GIVEN_request_is_empty_WHEN_criarAssociado_DO_client_error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void GIVEN_nothing_WHEN_recuperarTodosAssociados_DO_lista_de_associados() throws Exception {
        when(associadoService.recuperarTodos()).thenReturn(listResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(listResponse)));
    }

    @Test
    public void GIVEN_associadoId_WHEN_associadoPodeVotar_DO_resultado_pode_votar() throws Exception {
        String associadoId  = "associado_id";
        String podeVotar = "Usuário pode votar";
        when(associadoService.podeVotar(anyString())).thenReturn("Usuário pode votar");
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + associadoId)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(podeVotar));
        verify(associadoService).podeVotar(associadoId);
    }

}