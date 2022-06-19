package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.request.AssociadoRequest;
import com.example.sicredidemo.domain.response.AssociadoResponse;
import com.example.sicredidemo.entity.AssociadoEntity;
import com.example.sicredidemo.exception.SicrediErroException;
import com.example.sicredidemo.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações do Associado
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final CPFManagerService cpfManagerService;

    /**
     * Método que cria um associado utilizando o DTO passado
     *
     * @param associado
     * @return
     */
    public AssociadoResponse criarAssociado(AssociadoRequest associado) {
        AssociadoEntity associadoEntity = AssociadoEntity.builder()
                .id(UUID.randomUUID().toString())
                .cpf(associado.getCpf())
                .valido(cpfManagerService.isCPF(associado.getCpf()))
                .build();
        return AssociadoResponse.makeResponse(associadoRepository.save(associadoEntity));
    }

    /**
     * Método para recuperar todos os associados cadastrados no sistema
     *
     * @return
     */
    public List<AssociadoResponse> recuperarTodos() {
        return associadoRepository.findAll().stream().map(s -> {
            return AssociadoResponse.makeResponse(s);
        }).collect(Collectors.toList());
    }

    /**
     * Método para validar se o usuário pode votar, realizando uma validação externa
     *
     * @param associadoId
     * @return
     */
    public String podeVotar(String associadoId) {
        AssociadoEntity associadoEntity = associadoRepository.findById(associadoId).orElseThrow(() ->
                new SicrediErroException("Associado não encontrado para o ID : " + associadoId, NOT_FOUND));

        if (!cpfManagerService.isUsuarioPodeVotar(associadoEntity.getCpf())) {
            return "Usuário não pode votar no momento";
        }
        return "Usuário pode votar";
    }

}
