package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.external.UsuarioStatus;
import com.example.sicredidemo.enums.UsuarioStatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CPFManagerServiceTest {

    @InjectMocks
    private CPFManagerService service;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup(){
        ReflectionTestUtils.setField(service,
                "validadorUsuarioUrl",
                "http://localhost/external");
    }

    @Test
    public void GIVEN_cpf_valid_WHEN_isUsuarioPodeVotar_DO_return_pode_votar() {
        String cpf = "cpf";
        UsuarioStatus externalResponse = new UsuarioStatus();
        externalResponse.setStatus(UsuarioStatusEnum.ABLE_TO_VOTE);
        ResponseEntity response = new ResponseEntity(externalResponse, HttpStatus.OK);
        when(restTemplate.exchange(service.getUri(cpf), HttpMethod.GET, service.getHttpEntity(), UsuarioStatus.class)).thenReturn(response);

        boolean usuarioPodeVotar = service.isUsuarioPodeVotar(cpf);

        assertEquals(true, usuarioPodeVotar);
        verify(restTemplate).exchange(service.getUri(cpf), HttpMethod.GET, service.getHttpEntity(), UsuarioStatus.class);
    }

    @Test
    public void GIVEN_cpf_not_valid_WHEN_isUsuarioPodeVotar_DO_return_nao_pode_votar() {
        String cpf = "cpf";
        UsuarioStatus externalResponse = new UsuarioStatus();
        externalResponse.setStatus(UsuarioStatusEnum.UNABLE_TO_VOTE);
        ResponseEntity response = new ResponseEntity(externalResponse, HttpStatus.OK);
        when(restTemplate.exchange(service.getUri(cpf), HttpMethod.GET, service.getHttpEntity(), UsuarioStatus.class)).thenReturn(response);

        boolean usuarioPodeVotar = service.isUsuarioPodeVotar(cpf);

        assertEquals(false, usuarioPodeVotar);
        verify(restTemplate).exchange(service.getUri(cpf), HttpMethod.GET, service.getHttpEntity(), UsuarioStatus.class);
    }

}
