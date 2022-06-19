package com.example.sicredidemo.controller;


import com.example.sicredidemo.controller.PautaController;
import com.example.sicredidemo.domain.request.PautaRequest;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.service.PautaService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PautaControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController controller;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String URL_BASE = "/v1/pauta";
    private static final PautaRequest request = new PautaRequest();
    private static final List<PautaResponse> listResponse = List.of(PautaResponse.builder().build());

    @Test
    public void GIVEN_request_is_ok_WHEN_criarPauta_DO_associado_criado() throws Exception {
        when(pautaService.criarPauta(any(PautaRequest.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
        verify(pautaService).criarPauta(request);
    }

    @Test
    public void GIVEN_request_is_empty_WHEN_criarPauta_DO_client_error() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(""));
    }

    @Test
    public void GIVEN_nothing_WHEN_recuperarTodosPautas_DO_lista_de_associados() throws Exception {
        when(pautaService.recuperarTodos()).thenReturn(listResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(listResponse)));
    }

}