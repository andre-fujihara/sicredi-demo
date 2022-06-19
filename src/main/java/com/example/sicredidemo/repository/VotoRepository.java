package com.example.sicredidemo.repository;

import com.example.sicredidemo.entity.VotoEntity;
import com.example.sicredidemo.enums.ValorVotoEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface VotoRepository extends MongoRepository<VotoEntity, String> {

    /**
     * Método para procurar os votos referentes ao par pautaId e associadoId passados, pois não é permitido mais de um voto em uma mesma pauta por associado
     *
     * @param pautaId
     * @param associadoId
     * @return
     */
    Optional<VotoEntity> findByPautaIdAndAssociadoId(String pautaId, String associadoId);

    /**
     * Método que conta os votos com valor do enum passado para determinada pauta em qualquer estágio
     *
     * @param pautaId
     * @param voto
     * @return
     */
    long countByPautaIdAndVoto(String pautaId, ValorVotoEnum voto);
}
