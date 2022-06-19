package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.request.PautaRequest;
import com.example.sicredidemo.domain.response.PautaResponse;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço que realiza as manipulações das Pautas
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {

    private final PautaRepository pautaRepository;

    /**
     * Método para criar uma nova pauta a partir dos parâmetros da request
     *
     * @param pautaRequest
     * @return
     */
    public PautaResponse criarPauta(PautaRequest pautaRequest) {
        PautaEntity pautaEntity = pautaRepository.save(PautaEntity.builder()
                .id(UUID.randomUUID().toString())
                .nome(pautaRequest.getNome())
                .build());
        return PautaResponse.makeResponse(pautaEntity);
    }

    /**
     * Método para retornar todas as pautas do sistema
     *
     * @return
     */
    public List<PautaResponse> recuperarTodos() {
        return pautaRepository.findAll().stream().map(p -> {
            return PautaResponse.makeResponse(p);
        }).collect(Collectors.toList());
    }
}
