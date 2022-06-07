package com.example.sicredidemo.data.repositories;

import com.example.sicredidemo.data.entities.VotoEntity;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface VotoRepository extends MongoRepository<VotoEntity, UUID> {

    /**
     * Método para procurar os votos referentes ao par pautaId e associadoId passados, pois não é permitido mais de um voto em uma mesma pauta por associado
     *
     * @param pautaId
     * @param associadoId
     * @return
     */
    Optional<VotoEntity> findByPautaIdAndAssociadoId(UUID pautaId, UUID associadoId);

    /**
     * Método que conta os votos com valor do enum passado para determinada pauta em qualquer estágio
     *
     * @param pautaId
     * @param voto
     * @return
     */
    long countByPautaIdAndVoto(UUID pautaId, ValorVotoEnum voto);
}
