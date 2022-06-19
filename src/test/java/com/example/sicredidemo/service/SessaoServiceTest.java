//package com.example.sicredidemo.service;
//
//import com.example.sicredidemo.domain.request.AssociadoRequest;
//import com.example.sicredidemo.domain.response.AssociadoResponse;
//import com.example.sicredidemo.entity.AssociadoEntity;
//import com.example.sicredidemo.exception.SicrediErroException;
//import com.example.sicredidemo.repository.AssociadoRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SessaoServiceTest {
//
//    @InjectMocks
//    private AssociadoService service;
//    @Mock
//    private AssociadoRepository associadoRepository;
//    @Mock
//    private CPFManagerService cpfManagerService;
//
//    @Test
//    public void GIVEN_AssociadoRequest_WHEN_criarAssociado_DO_AssociadoResponse() {
//        AssociadoRequest request = new AssociadoRequest();
//        AssociadoEntity associadoEntity = AssociadoEntity.builder()
//                .id(UUID.randomUUID().toString())
//                .cpf("CPF")
//                .build();
//        when(cpfManagerService.isCPF(null)).thenReturn(false);
//        when(associadoRepository.save(any(AssociadoEntity.class))).thenReturn(associadoEntity);
//
//        AssociadoResponse associadoResponse = service.criarAssociado(request);
//
//        assertEquals(AssociadoResponse.makeResponse(associadoEntity), associadoResponse);
//        verify(cpfManagerService).isCPF(null);
//        verify(associadoRepository).save(any(AssociadoEntity.class));
//    }
//
//    @Test
//    public void GIVEN_nothing_WHEN_recuperarTodos_DO_list_associado_response() {
//        List<AssociadoEntity> listAssociado = List.of(AssociadoEntity.builder().build());
//        List<AssociadoResponse> ListAssociadoresponse = listAssociado.stream().map(s -> {
//            return AssociadoResponse.makeResponse(s);
//        }).collect(Collectors.toList());
//        when(associadoRepository.findAll()).thenReturn(listAssociado);
//
//        List<AssociadoResponse> associadoResponses = service.recuperarTodos();
//
//        assertEquals(ListAssociadoresponse, associadoResponses);
//        verify(associadoRepository).findAll();
//    }
//
//    @Test
//    public void GIVEN_associado_WHEN_podeVotar_DO_usuario_pode_votar() {
//        String associadoId = "associado_id";
//        AssociadoEntity associadoEntity = AssociadoEntity.builder().cpf("cpf").build();
//        when(associadoRepository.findById(associadoId)).thenReturn(Optional.of(associadoEntity));
//        when(cpfManagerService.isUsuarioPodeVotar(anyString())).thenReturn(true);
//
//        String podeVotar = service.podeVotar(associadoId);
//
//        assertEquals("Usuário pode votar", podeVotar);
//        verify(associadoRepository).findById(associadoId);
//        verify(cpfManagerService).isUsuarioPodeVotar(associadoEntity.getCpf());
//    }
//
//    @Test
//    public void GIVEN_associado_WHEN_podeVotar_DO_usuario_nao_pode_votar() {
//        String associadoId = "associado_id";
//        AssociadoEntity associadoEntity = AssociadoEntity.builder().cpf("cpf").build();
//        when(associadoRepository.findById(associadoId)).thenReturn(Optional.of(associadoEntity));
//        when(cpfManagerService.isUsuarioPodeVotar(anyString())).thenReturn(false);
//
//        String podeVotar = service.podeVotar(associadoId);
//
//        assertEquals("Usuário não pode votar no momento", podeVotar);
//        verify(associadoRepository).findById(associadoId);
//        verify(cpfManagerService).isUsuarioPodeVotar(associadoEntity.getCpf());
//    }
//
//    @Test
//    public void GIVEN_associado_nao_encontrado_WHEN_podeVotar_DO_associado_nao_encotrado() {
//        String associadoId = "associado_id";
//        SicrediErroException expectedException = new SicrediErroException("Associado não encontrado para o ID : " + associadoId, NOT_FOUND);
//        when(associadoRepository.findById(associadoId)).thenReturn(Optional.ofNullable(null));
//
//        SicrediErroException sicrediErroException = assertThrows(SicrediErroException.class, () -> service.podeVotar(associadoId));
//
//        assertEquals(expectedException, sicrediErroException);
//        verify(associadoRepository).findById(associadoId);
//    }
//}
